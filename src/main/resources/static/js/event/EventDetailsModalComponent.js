import CategoryBadge from './EventCategoryBadgeComponent.js'; 

export default {
    name: 'EventDetailsModalComponent',
    components: {
        CategoryBadge
    },
    props: {
        eventData: {
            type: Object,
            required: true
        }
    },
    emits: ['close'], 
    methods: {
        closeModal() {
            this.$emit('close'); 
        },
        formatDateTime(datetimeStr) {
            return datetimeStr.replace('T', ' | ').slice(0, 18);
        },
        formatTime(timeStr) {
            const [hour, minute] = timeStr.split(':');
            return `${hour}:${minute}`;
        }
    },
    template: `
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title pt-3">
                    <h5>{{ eventData.name }}
                        <CategoryBadge :category="eventData.category" class="align-items-center modal-course-cat"></CategoryBadge>
                    </h5>
                </div>
                <button type="button" class="btn-close" aria-label="Close" @click="closeModal"></button>
            </div>
            <div class="modal-body pt-1"> 
                <div>{{ eventData.description }}</div>
                <div class="pt-4 row">
                    <div class="col-6"> 
                        <strong>Venue:</strong> <br> {{ eventData.venue }}
                    </div>
                    <div class="col-6"> 
                        <strong>Cancellation Fee:</strong> <br>$ {{ eventData.cancellationFee }}
                    </div>
                </div>
                <div class="pt-4 row">
                    <div class="col-6"> 
                    <strong>Date:</strong> <br> {{ eventData.eventStartDate }}
                    </div>
                    <div class="col-6">
                    <strong>Time:</strong> <br> {{ formatTime(eventData.eventStartTime) }} to {{ formatTime(eventData.eventEndTime) }}
                    </div>
                </div>
                <div class="pt-4 row">
                    <div class="col-6"> 
                        <strong>Sales Start Date & Time:</strong> <br> {{ formatDateTime(eventData.ticketSaleStartDateTime) }}
                    </div>
                    <div class="col-6">
                        <strong>Sales End Date & Time:</strong> <br>  {{ formatDateTime(eventData.ticketSaleEndDateTime)}}
                    </div>
                </div>
                <div class="pt-4" v-if="eventData.seats && eventData.seats.length > 0">
                    <div><strong>Seating Options: </strong></div>
                    <table class="table table-bordered border-dark">
                        <thead>
                            <tr>
                                <th>Type</th>
                                <th>Cost ($)</th>
                                <th>Total No. of Seats</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="option in eventData.seats" :key="option.type">
                                <td>{{ option.type }}</td>
                                <td>{{ option.cost }}</td>
                                <td>{{ option.numberOfSeats }} </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    `
};
