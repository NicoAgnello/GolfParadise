const { createApp } = Vue;

createApp({
  data() {
    return {
      firsName: "",
      email: "",
      phone: "",
      message: "",
      client: null,
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      axios
        .get("/api/clients/current")
        .then((res) => {
          this.client = res.data;
          console.log(this.client);
        })
        .catch((err) => console.log(err));
    },
    sendContact() {
      if (this.email !== "" && this.email !== "" && this.message !== "") {
        Swal.fire({
          title: "Good!",
          text: "Message sent successfully!.",
          imageUrl: "./assets/mood-smile-beam.svg",
          imageWidth: 400,
          imageHeight: 200,
          imageAlt: "Custom image",
          background: "#FCE6BE",
          confirmButtonColor: "#598526",
        });
      }
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
          }).then(() => {
            location.href = "./landing.html";
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
}).mount("#app");
