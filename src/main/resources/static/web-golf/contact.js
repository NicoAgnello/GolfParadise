const { createApp } = Vue;

createApp({
  data() {
    return {};
  },
  created() {},
  methods: {
    sendContact() {
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
            location.href = "./web-golf/landing.html";
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
}).mount("#app");
