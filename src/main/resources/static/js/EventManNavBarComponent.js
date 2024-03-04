export default {
    props: {
        activeLink: String // Prop to specify the active link
    },
    template: `
        <nav class="navbar navbar-expand-lg navbar-light bg-light mb-5">
            <div class="container-fluid mt-2">
                <a class="navbar-brand no-underline" id="navbar-link">
                    <span class="system-name">
                        <span class="vertical-line"></span>
                        Ticket Booking <br />System
                    </span>
                </a>

                <button class="navbar-toggler custom-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse navcontent" id="navbarNav">
                    <ul class="navbar-nav ms-auto navbar-menu">
                        <li class="nav-item">
                            <a class="nav-link" :class="{ 'active': activeLink === 'viewEvents' }" href="/">View Events</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" :class="{ 'active': activeLink === 'createEvent' }" href="/eventManCreateEvent">Create Event</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" :class="{ 'active': activeLink === 'viewTicOfficer' }" href="/eventManViewTicOfficer">View Ticketing Officer</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" :class="{ 'active': activeLink === 'dashboard' }" href="/">Dashboard</a>
                        </li>
                        <li class="nav-item">
                            <button class="btn sign-out-button" @click="signOut">Sign Out</button>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    `,
    methods: {
        signOut() {
            // Add sign out functionality here
        }
    }
};
