export default {
  name: "EventFormComponent",
  props: {
    mode: {
      type: String,
      required: true,
      validator: function (value) {
        return ["create", "edit"].includes(value.toLowerCase());
      },
    },
    eventData: {
      type: Object,
      default: null,
    },
  },
  data() {
    return {
      formData: {
        eventName: "",
        eventDescription: "",
        eventImageFile: "",
        eventCategory: "",
        eventVenue: "",
        eventStartDate: "",
        eventEndDate: "",
        eventStartTime: "",
        eventEndTime: "",
        salesStartDate: "",
        salesEndDate: "",
        salesStartTime: "",
        salesEndTime: "",
        cancellationFee: 0,
        seatingOptions: [{ type: "", cost: 0, numberOfSeats: 0 }],
      },
      formErrors: {
        eventName: "",
        eventDescription: "",
        eventImageFile: "",
        eventVenue: "",
        eventStartDate: "",
        eventEndDate: "",
        salesStartDateTime: "",
        salesEndDateTime: "",
        cancellationFee: "",
        seatingOptionsErrors: [{}],
      },
      eventData: {},
      isShowModal: false,
      modalTitle: "",
      modalBody: "",
    };
  },
  async mounted() {
    console.log("mounted");
    if (this.mode === "edit") {
      const eventId = window.location.pathname.split("/").pop();
      axios
        .get("/api/getEventDetails/" + eventId, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then(async (response) => {
          console.log(response.data);
          await this.convertEventDetailsKey(response.data);
        })
        .catch((error) => {
          console.error("Error fetching event data:", error);
        });
      console.log(this.eventData);
      if (this.mode === "edit" && this.eventData) {
        this.populateFormData();
      }
    }
  },
  methods: {
    populateFormData() {
      this.formData.eventName = this.eventData.eventName;
      this.formData.eventDescription = this.eventData.eventDescription;
      this.formData.eventImageFile = this.eventData.eventImageFile;
      this.formData.eventCategory = this.eventData.eventCategory;
      this.formData.eventVenue = this.eventData.eventVenue;
      this.formData.eventStartDate = this.eventData.eventStartDate;
      this.formData.eventEndDate = this.eventData.eventEndDate;
      this.formData.eventStartTime = this.eventData.eventStartTime;
      this.formData.eventEndTime = this.eventData.eventEndTime;
      this.formData.salesStartDate = this.eventData.salesStartDate;
      this.formData.salesEndDate = this.eventData.salesEndDate;
      this.formData.salesStartTime = this.eventData.salesStartTime;
      this.formData.salesEndTime = this.eventData.salesEndTime;
      this.formData.cancellationFee = this.eventData.cancellationFee;
      this.formData.seatingOptions = this.eventData.seatingOptions.map(
        (option) => ({
          type: option.type,
          cost: option.cost,
          numberOfSeats: option.numberOfSeats,
        })
      );
    },
    convertToTimeStamp(dateString, timeString) {
      const salesEndDate = new Date(dateString);
      const [hours, minutes] = timeString.split(":");
      const salesEndTime = new Date();
      salesEndTime.setHours(parseInt(hours, 10));
      salesEndTime.setMinutes(parseInt(minutes, 10));
      salesEndTime.setSeconds(0);
      salesEndTime.setMilliseconds(0);
      const combinedDateTime = new Date(salesEndDate);
      combinedDateTime.setHours(salesEndTime.getHours());
      combinedDateTime.setMinutes(salesEndTime.getMinutes());
      combinedDateTime.setSeconds(salesEndTime.getSeconds());
      const timestamp = combinedDateTime.toISOString();
      return timestamp;
    },
    submitForm() {
      this.formData.eventStartTime = this.formData.eventStartTime
        .split(":")
        .slice(0, 2)
        .join(":");
      this.formData.eventEndTime = this.formData.eventEndTime
        .split(":")
        .slice(0, 2)
        .join(":");
      let salesStartTimeOld = this.formData.salesStartTime;
      let salesEndTimeOld = this.formData.salesEndTime;
      this.formData.salesStartTime = this.convertToTimeStamp(
        this.formData.salesStartDate,
        this.formData.salesStartTime
      );
      this.formData.salesEndTime = this.convertToTimeStamp(
        this.formData.salesEndDate,
        this.formData.salesEndTime
      );
      console.log(this.formData.eventImageFile);
      console.log(this.formData);
      if (this.validateForm()) {
        if (this.mode === "create") {
          // Handle create event API call
          console.log("Creating event...");
          fetch("/api/manager/createEvent", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(this.formData),
          })
            .then((response) => {
              console.log(response);
              if (response.ok) {
                this.showModal(
                  "Success",
                  "You have successfully created the event!"
                );
                return response.json();
              } else {
                this.showModal(
                  "Error",
                  "There was a problem creating your event. Please try again."
                );
                throw new Error("Network response was not ok.");
              }
            })
            .then((data) => {
              console.log("Response from backend:", data);
            })
            .catch((error) => {
              this.showModal(
                "Error",
                "There was a problem creating your event. Please try again."
              );
              console.error("Error:", error);
            });
        } else if (this.mode === "edit") {
          // Handle edit event API call
          console.log("Editing event...");
          console.log(this.formData);
          const eventId = window.location.pathname.split("/").pop();
          axios
            .post("/api/manager/submitEditEvent/" + eventId, this.formData, {
              headers: {
                "Content-Type": "application/json",
              },
            })
            .then((response) => {
              console.log(response.data);
              this.showModal(
                "Success",
                "You have updated the event successfully!"
              );
            })
            .catch((error) => {
              this.showModal(
                "Error",
                "There was a problem updated your event. Please try again."
              );
              console.error("Error submitting edit event:", error);
            });
        }
      } else {
        console.log("Form validation failed");
        this.showModal(
          "Error",
          "Form Validation Failed. Refer to the error message displayed."
        );
      }
      this.formData.salesStartTime = salesStartTimeOld;
      this.formData.salesEndTime = salesEndTimeOld;
    },
    showModal(messageTitle, messageBody) {
      this.modalTitle = messageTitle;
      this.modalBody = messageBody;
      this.isShowModal = true;
    },
    hideModal() {
      if (this.modalTitle == "Error") {
        this.isShowModal = false;
      } else {
        this.isShowModal = false;
        window.location.href = "/manager/ViewEvents";
      }
    },
    handleImageUpload(event) {
      const file = event.target.files[0];
      console.log(file);
      if (file) {
        const reader = new FileReader();
        reader.onload = () => {
          const base64String = reader.result.split(",")[1];
          this.formData.eventImageFile = base64String;
          console.log(base64String);
          this.formErrors.eventImageFile = "";
        };
        reader.readAsDataURL(file);
      } else {
        if (this.mode === "create") {
          this.formErrors.eventImageFile = "An event image file is required.";
        }
      }
    },
    addSeatingOption() {
      this.formData.seatingOptions.push({
        type: "",
        cost: 0,
        numberOfSeats: 0,
      });
      this.formErrors.seatingOptionsErrors.push({});
    },
    removeSeatingOption(index) {
      this.formData.seatingOptions.splice(index, 1);
      this.formErrors.seatingOptionsErrors.splice(index, 1);
    },
    onCancel() {
      window.history.back();
    },
    combineDateTime(date, time) {
      return new Date(`${date}T${time}`);
    },
    validateEventName() {
      if (this.formData.eventName.length <= 5) {
        this.formErrors.eventName =
          "Event Name must be longer than 5 characters.";
        return false;
      }
      this.formErrors.eventName = "";
      return true;
    },

    validateEventDescription() {
      if (this.formData.eventDescription.length <= 5) {
        this.formErrors.eventDescription =
          "Event Description must be longer than 5 characters.";
        return false;
      }
      this.formErrors.eventDescription = "";
      return true;
    },

    validateImageUpload() {
      if (!this.formData.eventImageFile) {
        this.formErrors.eventImageFile = "An event image file is required.";
        return false;
      }
      this.formErrors.eventImageFile = "";
      return true;
    },

    validateEventVenue() {
      if (this.formData.eventVenue.length <= 5) {
        this.formErrors.eventVenue =
          "Event Venue must be longer than 5 characters.";
        return false;
      }
      this.formErrors.eventVenue = "";
      return true;
    },

    validateEventDateTime() {
      const today = new Date().toISOString().split("T")[0];

      if (
        !this.formData.eventStartDate ||
        !this.formData.eventStartTime ||
        !this.formData.eventEndDate ||
        !this.formData.eventEndTime
      ) {
        this.formErrors.eventStartDate = "Event date and time are required.";
        return false;
      }

      if (this.formData.eventStartDate <= today) {
        this.formErrors.eventStartDate =
          "Event start date cannot be today or in the past.";
        return false;
      }

      if (
        this.formData.eventEndTime &&
        this.combineDateTime(
          this.formData.eventStartDate,
          this.formData.eventEndTime
        ) <=
          this.combineDateTime(
            this.formData.eventStartDate,
            this.formData.eventStartTime
          )
      ) {
        this.formErrors.eventEndTime =
          "Event end time must be after the event start time.";
        return false;
      }

      if (this.formData.eventEndDate < this.formData.eventStartDate) {
        this.formErrors.eventEndDate =
          "Event end date must be after the event start date.";
        return false;
      }

      this.formErrors.eventStartDate = "";
      this.formErrors.eventEndDate = "";
      this.formErrors.eventEndTime = "";
      return true;
    },

    validateSalesStartDateTime() {
      const today = new Date().toISOString().split("T")[0];
      const salesStartDateTime = this.combineDateTime(
        this.formData.salesStartDate,
        this.formData.salesStartTime
      );
      const eventDateTime = this.combineDateTime(
        this.formData.eventStartDate,
        this.formData.eventStartTime
      );

      if (this.formData.salesStartDate < today) {
        this.formErrors.salesStartDateTime =
          "Sales start date cannot be in the past.";
        return false;
      }

      if (salesStartDateTime >= eventDateTime) {
        this.formErrors.salesStartDateTime =
          "Sales Start Date and Time must be before the Event Date and Time.";
        return false;
      }
      this.formErrors.salesStartDateTime = "";
      return true;
    },

    validateSalesEndDateTime() {
      const salesStartDateTime = this.combineDateTime(
        this.formData.salesStartDate,
        this.formData.salesStartTime
      );
      const salesEndDateTime = this.combineDateTime(
        this.formData.salesEndDate,
        this.formData.salesEndTime
      );
      const eventDateTime = this.combineDateTime(
        this.formData.eventStartDate,
        this.formData.eventStartTime
      );

      if (salesEndDateTime >= eventDateTime) {
        this.formErrors.salesEndDateTime =
          "Sales End Date and Time must be before the Event Date and Time.";
        return false;
      } else if (salesEndDateTime <= salesStartDateTime) {
        if (salesEndDateTime < salesStartDateTime) {
          this.formErrors.salesEndDateTime =
            "Sales End Date and Time must be after the Sales Start Date and Time.";
        } else {
          this.formErrors.salesEndDateTime =
            "Sales Start Date and Time cannot be the same as Sales End Date and Time.";
        }
        return false;
      }
      this.formErrors.salesEndDateTime = "";
      return true;
    },

    validateCancellationFee() {
      const cancellationFee = this.formData.cancellationFee;

      if (cancellationFee < 0) {
        this.formErrors.cancellationFee =
          "Cancellation Fee cannot be a negative number.";
        return false;
      }

      this.formErrors.salesEndDateTime = "";
      return true;
    },
    validateSeatingOptions() {
      this.formErrors.seatingOptionsErrors = this.formData.seatingOptions.map(
        (option) => {
          const errors = {};
          if (!option.type) errors.type = "Seat type is required.";
          if (option.cost <= 0) errors.cost = "Cost must be greater than 0.";
          if (option.numberOfSeats <= 0)
            errors.numberOfSeats = "Number of seats must be greater than 0.";
          return errors;
        }
      );

      return this.formErrors.seatingOptionsErrors.every(
        (errors) => Object.keys(errors).length === 0
      );
    },
    validateForm() {
      const validations = [
        this.validateEventName(),
        this.validateEventDescription(),
        this.validateImageUpload(),
        this.validateEventVenue(),
        this.validateEventDateTime(),
        this.validateSalesStartDateTime(),
        this.validateSalesEndDateTime(),
        this.validateCancellationFee(),
        this.validateSeatingOptions(),
      ];

      return validations.every((valid) => valid);
    },
    async convertEventDetailsKey(eventControllerData) {
      console.log("called method");
      const [ticketSaleEndDate, ticketSaleEndTime] = new Date(
        eventControllerData.eventDetails.ticketSaleEndDateTime
      )
        .toISOString()
        .split("T");
      const [ticketSaleStartDate, ticketSaleStartTime] = new Date(
        eventControllerData.eventDetails.ticketSaleStartDateTime
      )
        .toISOString()
        .split("T");

      const updatedEventData = {
        eventName: eventControllerData.eventDetails.name,
        eventDescription: eventControllerData.eventDetails.description,
        eventStartDate: eventControllerData.eventDetails.eventStartDate,
        eventEndDate: eventControllerData.eventDetails.eventEndDate,
        eventStartTime: eventControllerData.eventDetails.eventStartTime,
        eventEndTime: eventControllerData.eventDetails.eventEndTime,
        eventCategory: eventControllerData.eventDetails.eventCategory,
        eventImageFile: eventControllerData.eventDetails.image,
        eventVenue: eventControllerData.eventDetails.venue,
        salesEndDate: ticketSaleEndDate,
        salesEndTime: ticketSaleEndTime.slice(0, 8),
        salesStartDate: ticketSaleStartDate,
        cancellationFee: eventControllerData.eventDetails.cancellationFee,
        salesStartTime: ticketSaleStartTime.slice(0, 8),
        seatingOptions: eventControllerData.seats,
      };
      console.log(ticketSaleStartTime.slice(0, 8));
      this.formData = updatedEventData;
      console.log(this.formData);
    },
  },
  template: `
        
    <form method="post" role="form" v-on:submit.prevent="submitForm">

        <!--Event Name-->
        <div class="form-group mt-5 mb-4">
            <label for="eventName" class="mb-2">Event Name</label>
            <input v-model="formData.eventName" id="eventName" type="text" placeholder="Event Name" required class="form-control border-0 shadow-sm px-4 field" />
            <div v-if="formErrors.eventName" class="text-danger">{{ formErrors.eventName }}</div>
        </div>

        <!--Event Description-->
        <div class="form-group mb-4">
            <label for="eventDescription" class="mb-2">Event Description</label>
            <textarea v-model="formData.eventDescription" id="eventDescription" placeholder="Event Desc" required class="form-control border-0 shadow-sm px-4 textarea" rows="10"></textarea>
            <div v-if="formErrors.eventDescription" class="text-danger">{{ formErrors.eventDescription }}</div>
        </div>

        <!--Event Image Upload-->
        <div class="custom-file-container">
            <label for="eventImageFile" class="custom-file-label">Event Image</label>
            <input type="file" id="eventImageFile" @change="handleImageUpload" class="custom-file-input" />
            <div v-if="formErrors.eventImageFile" class="text-danger">{{ formErrors.eventImageFile }}</div>
        </div>

        <!--Event Category-->
        <div class="form-group mb-4">
            <label for="eventCategory" class="mb-2">Event Category</label>
            <select v-model="formData.eventCategory" id="eventCategory" class="form-select border-0 shadow-sm px-4 field">
                <option disabled value="">Category</option>
                <option value="Concert">Concert</option>
                <option value="Theatre Shows">Theatre Shows</option>
                <option value="Seminars">Seminars</option>
            </select>
        </div>

        <!--Event Venue-->
        <div class="form-group mb-4">
            <label for="eventVenue" class="mb-2">Event Venue</label>
            <input v-model="formData.eventVenue" id="eventVenue" type="text" placeholder="Venue" required class="form-control border-0 shadow-sm px-4 field" />
            <div v-if="formErrors.eventVenue" class="text-danger">{{ formErrors.eventVenue }}</div>
        </div>

        <!--Event Date and Time-->
        <div class="row mb-4">
            <div class="col-md-6 form-group">
                <label for="eventStartDate" class="mb-2">Event Start Date</label>
                <input v-model="formData.eventStartDate" type="date" id="eventStartDate" required class="form-control border-0 shadow-sm px-4 field" />
                <div v-if="formErrors.eventStartDate" class="text-danger">{{ formErrors.eventStartDate }}</div>
            </div>
            <div class="col-md-6 form-group starttime-field">
                <label for="eventEndDate" class="mb-2">Event End Date</label>
                <input v-model="formData.eventEndDate" type="date" id="eventEndDate" required class="form-control border-0 shadow-sm px-4 field" />
                <div v-if="formErrors.eventEndDate" class="text-danger">{{ formErrors.eventEndDate }}</div>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-6 form-group">
                <label for="eventStartTime" class="mb-2">Event Start Time</label>
                <input v-model="formData.eventStartTime" type="time" id="eventStartTime" required class="form-control border-0 shadow-sm px-4 field" />
            </div>
            <div class="col-md-6 form-group starttime-field">
                <label for="eventEndTime" class="mb-2">Event End Time</label>
                <input v-model="formData.eventEndTime" type="time" id="eventEndTime" required class="form-control border-0 shadow-sm px-4 field" />
                <div v-if="formErrors.eventEndTime" class="text-danger">{{ formErrors.eventEndTime }}</div>
            </div>
        </div>

        <!--Sales Date and Time-->
        <div class="row mb-4">
            <div class="col-md-6 form-group">
                <label for="salesStartDate" class="mb-2">Sales Start Date</label>
                <input v-model="formData.salesStartDate" type="date" id="salesStartDate" required class="form-control border-0 shadow-sm px-4 field" />
            </div>
            <div class="col-md-6 form-group starttime-field">
                <label for="salesStartTime" class="mb-2">Sales Start Time</label>
                <input v-model="formData.salesStartTime" type="time" id="salesStartTime" required class="form-control border-0 shadow-sm px-4 field" />
            </div>
            <div v-if="formErrors.salesStartDateTime" class="col-md-12 text-danger">{{ formErrors.salesStartDateTime }}</div>
        </div>

        <div class="row mb-4">
            <div class="col-md-6 form-group">
                <label for="salesEndDate" class="mb-2">Sales End Date</label>
                <input v-model="formData.salesEndDate" type="date" id="salesEndDate" required class="form-control border-0 shadow-sm px-4 field" />
            </div>
            <div class="col-md-6 form-group starttime-field">
                <label for="salesEndTime" class="mb-2">Sales End Time</label>
                <input v-model="formData.salesEndTime" type="time" id="salesEndTime" required class="form-control border-0 shadow-sm px-4 field" />
            </div>
            <div v-if="formErrors.salesEndDateTime" class="col-md-12 text-danger">{{ formErrors.salesEndDateTime }}</div>
        </div>

        <div class="row mb-5">
            <div class="form-group mb-2">
                <label>Cancellation Fee</label>
                <input v-model="formData.cancellationFee" type="number" min="0" placeholder="Cancellation fee" class="form-control border-0 shadow-sm px-4 field" />
                <span class="text-danger">{{ formErrors.cancellationFee }}</span>
            </div>
        </div>


        <!-- Seating Options Section -->
        <div class="seating-options-section">
            <h3>Seating Options</h3>
            <div v-for="(option, index) in formData.seatingOptions" :key="index" class="seating-option mb-3">
                <h5 class="mt-4 mb-4"><u>Option {{ index + 1 }}</u></h5>

                <div class="form-group mb-4">
                    <label>Seat Type</label>
                    <input v-model="option.type" type="text" placeholder="VIP, Cat 1, Cat 2, etc." class="form-control border-0 shadow-sm px-4 field" />
                    <span class="text-danger">{{ formErrors.seatingOptionsErrors[index]?.type }}</span>
                </div>
                
                <div class="form-group mb-4">
                    <label>Cost per Seat</label>
                    <input v-model.number="option.cost" type="number" min="0" placeholder="Cost" class="form-control border-0 shadow-sm px-4 field" />
                    <span class="text-danger">{{ formErrors.seatingOptionsErrors[index]?.cost }}</span>
                </div>
                
                <div class="form-group mb-4">
                    <label>Number of Seats</label>
                    <input v-model.number="option.numberOfSeats" type="number" min="0" placeholder="Number of seats" class="form-control border-0 shadow-sm px-4 field" />
                    <span class="text-danger">{{ formErrors.seatingOptionsErrors[index]?.numberOfSeats }}</span>
                </div>
                
                <div style="text-align: right;">
                    <span style="cursor: pointer;" class="text-danger" @click="removeSeatingOption(index)">
                        <u>- Remove Option</u>
                    </span>
                </div>
            </div>

            <div class="text-center">
                <span style="cursor: pointer;" class="text-secondary" @click="addSeatingOption">
                    <u>+ Add Seating Option</u>
                </span>
            </div>
            
        </div>

        <!--Buttons-->
        <div class="row mt-4">
            <div class="col-sm-6 form-group mb-4">
                <button type="reset" class="btn btn-secondary btn-block shadow-sm w-100 cancelbtn" @click="onCancel">
                        Cancel
                </button>
            </div>
            <div class="col-sm-6 form-group mb-4">
                <button type="submit" class="btn btn-primary btn-block shadow-sm w-100 submitbtn">
                        {{ mode === 'create' ? 'Create Event' : 'Update Event' }}
                </button>
            </div>
        </div>

        <!-- Modal -->
        <div v-if="isShowModal" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">{{ modalTitle }}</h5>
                        <button type="button" class="btn-close" @click="hideModal"></button>
                    </div>
                    <div class="modal-body">
                        <p>{{ modalBody }}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" @click="hideModal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    `,
};
