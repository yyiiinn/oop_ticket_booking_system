export default {
    template: `
        <span class="badge" style="background-color: #3498db;" v-if="category === 'Concert'">Concert</span>
        <span class="badge" style="background-color: #9b59b6;" v-if="category === 'Theatre Shows'">Theatre Shows</span>
        <span class="badge" style="background-color: #27ae60;" v-if="category === 'Seminars'">Seminars</span>
        `,
    props: {
      category: String,
    },
  };
  