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
    logOut() {
      axios
        .post("/api/logout")
        .then(() => {
          const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true,
            didOpen: (toast) => {
              toast.addEventListener("mouseenter", Swal.stopTimer);
              toast.addEventListener("mouseleave", Swal.resumeTimer);
            },
          });

          Toast.fire({
            icon: "warning",
            title: "Log out successfully",
          });
        })
        .then(() => {
          location.href = "../web-golf/landing.html";
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
}).mount("#app");
