const { createApp } = Vue;

window.onscroll = function () {
  if (document.documentElement.scrollTop > 100) {
    document.querySelector(".go-top-container").classList.add("show-own");
  } else {
    document.querySelector(".go-top-container").classList.remove("show-own");
  }
};
document.querySelector(".go-top-container").addEventListener("click", () => {
  window.scrollTo({
    top: 0,
    behavior: "smooth",
  });
});

createApp({
  data() {
    return {
      products: [],
      lastProducts: [],
      listOfTwoProducts: [],
      clubsProducts: [],
      bagsProducts: [],
      ballsProducts: [],
      clothesProducts: [],
      accesoriesProducts: [],
      shoesProducts: [],
      client: null,
    };
  },
  created() {
    this.loadData();
    this.getCurrentClient();
  },

  mounted() {},
  methods: {
    loadData() {
      axios("/api/products").then((response) => {
        this.products = response.data;
        this.lastProducts = this.products.sort((a, b) => b.id - a.id).slice(0, 8);

        this.clubsProducts = this.products.filter((product) => product.category == "CLUBS").slice(0, 2);
        this.bagsProducts = this.products.filter((product) => product.category == "BAGS").slice(0, 2);
        this.ballsProducts = this.products.filter((product) => product.category == "BALLS").slice(0, 2);
        this.clothesProducts = this.products.filter((product) => product.category == "CLOTHES").slice(0, 2);
        this.accesoriesProducts = this.products.filter((product) => product.category == "ACCESORIES").slice(0, 2);
        this.shoesProducts = this.products.filter((product) => product.category == "SHOES").slice(0, 2);

        this.listOfTwoProducts = [
          ...this.clubsProducts,
          ...this.bagsProducts,
          ...this.ballsProducts,
          ...this.clothesProducts,
          ...this.accesoriesProducts,
          ...this.shoesProducts,
        ].sort(() => Math.random() - 0.5);
        console.log(this.listOfTwoProducts);
      });
    },
    getCurrentClient() {
      axios
        .get("/api/clients/current")
        .then((res) => {
          this.client = res.data;
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
          }).then(() => {
            location.href = "./landing.html";
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  computed: {},
}).mount("#app");
