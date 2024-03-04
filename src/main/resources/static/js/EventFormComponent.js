export default {
    name: 'EventFormComponent',
    props: {
        mode: {
            type: String,
            required: true,
            validator: function(value) {
                return ['create', 'edit'].includes(value.toLowerCase());
            }
        },
        eventData: {
            type: Object,
            default: null
        }
    },
    data() {
        return {
            formData: {
                eventName: '',
                eventDescription: '',
                eventImageFile: '',
                eventCategory: '', 
                eventVenue: '',
                eventDate: '',
                eventStartTime: '',
                eventEndTime: '',
                salesStartDate: '',
                salesEndDate: '',
                salesStartTime: '',
                salesEndTime: '',
                seatingOptions: [
                    { type: '', cost: 0, numberOfSeats: 0, cancellationFee: 0 }
                ]
            },
            formErrors: {
                eventName: '',
                eventDescription: '',
                eventImageFile: '',
                eventVenue: '',
                salesStartDateTime: '',
                salesEndDateTime: '',
                seatingOptionsErrors: [{}]
            }
        };
    },
    mounted() {
        console.log(this.eventData)
        if (this.mode === 'edit' && this.eventData) {
            this.populateFormData();
        }
    },
    methods: {
        populateFormData() {
            this.formData.eventName = this.eventData.eventName;
            this.formData.eventDescription = this.eventData.eventDescription;
            this.formData.eventImageFile = this.eventData.eventImageFile; // Assuming eventImageFile is directly assigned
            this.formData.eventCategory = this.eventData.eventCategory;
            this.formData.eventVenue = this.eventData.eventVenue;
            this.formData.eventDate = this.eventData.eventDate;
            this.formData.eventStartTime = this.eventData.eventStartTime;
            this.formData.eventEndTime = this.eventData.eventEndTime;
            this.formData.salesStartDate = this.eventData.salesStartDate;
            this.formData.salesEndDate = this.eventData.salesEndDate;
            this.formData.salesStartTime = this.eventData.salesStartTime;
            this.formData.salesEndTime = this.eventData.salesEndTime;
            this.formData.seatingOptions = this.eventData.seatingOptions.map(option => ({
                type: option.type,
                cost: option.cost,
                numberOfSeats: option.numberOfSeats,
                cancellationFee: option.cancellationFee
            }));
        },        
        submitForm() {
            if (this.validateForm()) {
                if (this.mode === 'create') {
                    // Handle create event API call
                    console.log("Creating event...");
                } else if (this.mode === 'edit') {
                    // Handle edit event API call
                    console.log("Editing event...");
                }
            } else {
                console.log("Form validation failed");
            }
        },
        handleImageUpload(event) {
            const file = event.target.files[0];
            if (file) {
                // change it based on supabase approach
                this.formData.eventImageFile = file;
                this.formErrors.eventImageFile = '';
            } else {
                if (this.mode === 'create') {
                    this.formErrors.eventImageFile = 'An event image file is required.';
                }
            }
        },
        addSeatingOption() {
            this.formData.seatingOptions.push({ type: '', cost: 0, numberOfSeats: 0, cancellationFee: 0 });
            this.formErrors.seatingOptionsErrors.push({});
        },
        removeSeatingOption(index) {
            this.formData.seatingOptions.splice(index, 1);
            this.formErrors.seatingOptionsErrors.splice(index, 1);
        },
        onReset() {
            // Reset form fields
            this.formData = {
                eventName: '',
                eventDescription: '',
                eventImageFile: null,
                eventCategory: '',
                eventVenue: '',
                eventDate: '',
                eventStartTime: '',
                eventEndTime: '',
                salesStartDate: '',
                salesEndDate: '',
                salesStartTime: '',
                salesEndTime: '',
                seatingOptions: [
                    { type: '', cost: 0, numberOfSeats: 0, cancellationFee: 0 }
                ]
            };

            this.formErrors = {
                eventName: '',
                eventDescription: '',
                eventImageFile: '',
                eventEndTime: '',
                salesStartDateTime: '',
                salesEndDateTime: '',
                seatingOptionsErrors: [{}]
            };
        },
        combineDateTime(date, time) {
            return new Date(`${date}T${time}`);
        },
        validateEventName() {
            if (this.formData.eventName.length <= 5) {
                this.formErrors.eventName = 'Event Name must be longer than 5 characters.';
                return false;
            }
            this.formErrors.eventName = '';
            return true;
        },

        validateEventDescription() {
            if (this.formData.eventDescription.length <= 5) {
                this.formErrors.eventDescription = 'Event Description must be longer than 5 characters.';
                return false;
            }
            this.formErrors.eventDescription = '';
            return true;
        },

        validateImageUpload() {
            if (!this.formData.eventImageFile) {
                this.formErrors.eventImageFile = 'An event image file is required.';
                return false;
            }
            this.formErrors.eventImageFile = '';
            return true;
        },

        validateEventVenue() {
            if (this.formData.eventVenue.length <= 5) {
                this.formErrors.eventVenue = 'Event Venue must be longer than 5 characters.';
                return false;
            }
            this.formErrors.eventVenue = '';
            return true;
        },
        
        validateEventDateTime() {
            if (!this.formData.eventDate || !this.formData.eventStartTime) {
                this.formErrors.eventDate = 'Event date and start time are required.';
                return false;
            }
            if (this.formData.eventEndTime && this.combineDateTime(this.formData.eventDate, this.formData.eventEndTime) <= this.combineDateTime(this.formData.eventDate, this.formData.eventStartTime)) {
                this.formErrors.eventEndTime = 'Event end time must be after the event start time.';
                return false;
            }
            this.formErrors.eventDate = '';
            this.formErrors.eventEndTime = '';
            return true;
        },
        
        validateSalesStartDateTime() {
            const salesStartDateTime = this.combineDateTime(this.formData.salesStartDate, this.formData.salesStartTime);
            const eventDateTime = this.combineDateTime(this.formData.eventDate, this.formData.eventStartTime);

            if (salesStartDateTime >= eventDateTime) {
                this.formErrors.salesStartDateTime = 'Sales Start Date and Time must be before the Event Date and Time.';
                return false;
            }
            this.formErrors.salesStartDateTime = '';
            return true;
        },
        validateSalesEndDateTime() {
            const salesStartDateTime = this.combineDateTime(this.formData.salesStartDate, this.formData.salesStartTime);
            const salesEndDateTime = this.combineDateTime(this.formData.salesEndDate, this.formData.salesEndTime);
            const eventDateTime = this.combineDateTime(this.formData.eventDate, this.formData.eventStartTime);

            if (salesEndDateTime >= eventDateTime) {
                this.formErrors.salesEndDateTime = 'Sales End Date and Time must be before the Event Date and Time.';
                return false;
            } else if (salesEndDateTime <= salesStartDateTime) {
                this.formErrors.salesEndDateTime = 'Sales End Date and Time must be after the Sales Start Date and Time.';
                return false;
            }
            this.formErrors.salesEndDateTime = '';
            return true;
        },
        validateSeatingOptions() {
            this.formErrors.seatingOptionsErrors = this.formData.seatingOptions.map(option => {
                const errors = {};
                if (!option.type) errors.type = 'Seat type is required.';
                if (option.cost <= 0) errors.cost = 'Cost must be greater than 0.';
                if (option.numberOfSeats <= 0) errors.numberOfSeats = 'Number of seats must be greater than 0.';
                if (option.cancellationFee < 0) errors.cancellationFee = 'Cancellation fee must not be negative.';
                return errors;
            });

            return this.formErrors.seatingOptionsErrors.every(errors => Object.keys(errors).length === 0);
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
                this.validateSeatingOptions()
            ];
        
            return validations.every(valid => valid);
        }
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
            <select v-model="formData.eventCategory" id="eventCategory" class="form-control border-0 shadow-sm px-4 field">
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
                <label for="eventDate" class="mb-2">Event Date</label>
                <input v-model="formData.eventDate" type="date" id="eventDate" required class="form-control border-0 shadow-sm px-4 field" />
            </div>
            <div class="col-md-6 form-group starttime-field">
                <label for="eventStartTime" class="mb-2">Event Start Time</label>
                <input v-model="formData.eventStartTime" type="time" id="eventStartTime" required class="form-control border-0 shadow-sm px-4 field" />
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-6 form-group">
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

        <div class="row mb-5">
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

        <!-- Seating Options Section -->
        <div class="seating-options-section">
            <h3>Seating Options</h3>
            <div v-for="(option, index) in formData.seatingOptions" :key="index" class="seating-option mb-3">
                <h5 class="mt-4 mb-4"><u>Option {{ index + 1 }}</u></h5>

                <div class="form-group mb-4">
                    <label>Seat Type</label>
                    <input v-model="option.type" type="text" placeholder="VIP, General, etc." class="form-control border-0 shadow-sm px-4 field" />
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
                
                <div class="form-group mb-2">
                    <label>Cancellation Fee</label>
                    <input v-model.number="option.cancellationFee" type="number" min="0" placeholder="Cancellation fee" class="form-control border-0 shadow-sm px-4 field" />
                    <span class="text-danger">{{ formErrors.seatingOptionsErrors[index]?.cancellationFee }}</span>
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
                <button type="reset" class="btn btn-secondary btn-block shadow-sm w-100 cancelbtn" @click="onReset">
                        Cancel
                </button>
            </div>
            <div class="col-sm-6 form-group mb-4">
                <button type="submit" class="btn btn-primary btn-block shadow-sm w-100 submitbtn">
                        {{ mode === 'create' ? 'Create Event' : 'Update Event' }}
                </button>
            </div>
        </div>
    </form>
    `
}