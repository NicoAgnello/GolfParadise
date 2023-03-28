const { createApp } = Vue;

createApp({
  data() {
    return {
      clients: [],
    };
  },
  created() {
    this.getClients();
  },
  methods: {
    getClients() {
      axios
        .get("/api/clients")
        .then((response) => {
          this.clients = response.data;
        })
        .catch((err) => console.log(err));
    },
  },
}).mount("#app");
