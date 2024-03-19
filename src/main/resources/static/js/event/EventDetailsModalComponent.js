import CategoryBadge from './EventCategoryBadgeComponent.js'; 

export default {
    name: 'EventDetailsModalComponent',
    components: {
        CategoryBadge
    },
    props: {
        eventData: {
            type: Object,
            required: true,


        },
        formatDate: Function,
        formatTimeDate: Function,
        formatTime: Function
    },
    emits: ['close'], 
    methods: {
        closeModal() {
            this.$emit('close'); 
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
                <div>{{ eventData.desc }}</div>
                <div class="pt-4 row">
                    <div class="col-6"> 
                    <strong>Venue:</strong> <br> {{ eventData.venue }}
                    </div>
                </div>
                
                <div class="pt-4 row">
                    <div class="col-6"> 
                    <strong>Cancellation Fee:</strong> <br> {{ eventData.cancellationCost }}
                    </div>
                </div>
                
                <div class="pt-4 row">
                    <div class="col-6"> 
                    <strong>Date:</strong> <br> {{ formatDate(eventData.date) }}
                    </div>
                    <div class="col-6">
                    <strong>Time:</strong> <br> {{ formatTime(eventData.startTime) }} to {{ formatTime(eventData.endTime) }}
                    </div>
                </div>
                <div class="pt-4 row">
                    <div class="col-6"> 
                        <strong>Sales Start DateTime:</strong> <br> {{ formatTimeDate(eventData.saleStart) }}
                    </div>
                    <div class="col-6">
                        <strong>Sales End DateTime:</strong> <br>  {{ formatTimeDate(eventData.saleEnd) }}
                    </div>
                </div>
                <div class="pt-4" v-if="eventData.seatingOptions && eventData.seatingOptions.length > 0">
                    <div><strong>Seating Options: </strong></div>
                    <table class="table table-bordered border-dark">
                        <thead>
                            <tr>
                                <th>Type</th>
                                <th>Cost ($)</th>
                                <th>Remaining Seats</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="option in eventData.seatingOptions" :key="option.type">
                                <td>{{ option.type }}</td>
                                <td>{{ option.cost }}</td>
                                <td>{{ option.numberOfSits }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    `
};
