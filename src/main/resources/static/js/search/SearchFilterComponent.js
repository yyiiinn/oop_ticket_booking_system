export default {
    name: "SearchFilterComponent",
    data() {
        return {
            eventName: "",
            category: "", 
            status: "",
            defaultCategoryText: "Event Category",
        };
    },
    props: {
        searchApi: Function,
        statuses: Array,
    },
    mounted() {
      
    },
    watch: {
        eventName(newValue) {
            
            clearTimeout(this.debouncedSearch);
            this.debouncedSearch = setTimeout(() => {
                this.searchFilter();
            }, 300);
        }
    },
    methods: {
        resetFilter() {
            this.eventName = ""; 
            this.searchFilter();
        },
        async searchFilter() {
            try {
                const eventName = this.eventName;
                const category = this.category; 

                let searchResults;
                
                // Use the searchApi function from the parent component
                searchResults = await this.searchApi(eventName, category);
                
                this.$emit("search-complete", searchResults);
            } catch (error) {
                console.error("Error fetching info:", error);
            }
        }
    },
    template: `
    <div id="searchfilter">
        <div class="container mb-2 pt-5">
            <form>
                <div class="row">
                    <div class="col-md">
                        <input v-model="eventName" type="text" placeholder="Event Name" ref="eventNameInput" name="eventName" class="form-control border-0 shadow-sm px-4 field mb-3"/>
                    </div>
                    <!-- Category Dropdown Field -->
                    <div class="col-md">
                        <div class="form-group mb-3 dropdown-field">
                            <select v-model="category" class="form-select border-0 shadow-sm px-4 field">
                                <option value="">{{ defaultCategoryText }}</option> <!-- Display default text when category is not selected -->
                                <option v-for="cat in ['Concert', 'Theatre Shows', 'Seminars']" :key="cat" :value="cat">{{ cat }}</option>
                            </select>
                        </div>
                    </div>
                    <!-- Status Dropdown Field -->
                    <div class="col-md">
                        <div class="form-group mb-3 dropdown-field">
                            <select v-model="status" class="form-select border-0 shadow-sm px-4 field">
                                <option value="">Event Status</option> <!-- Default option -->
                                <option v-for="statusOption in statuses" :key="statusOption" :value="statusOption">{{ statusOption }}</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md">
                        <div class="d-flex justify-content-between">
                            <button @click="resetFilter" class="btn filterbtn" id="resetbtn" button="type">Reset</button>
                            <button @click.prevent="searchFilter" class="btn filterbtn" id="searchbtn">Search</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    `
};
