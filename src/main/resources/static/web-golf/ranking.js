const { createApp } = Vue;

createApp({
  data() {
    return {
      players: [],
      year: 2022,
      elementsPerPage: 15,
      paginatedData: [],
      actualPage: 1,
      client: null,
    };
  },
  created() {
    this.loadData(this.year);
    this.getCurrentClient();
  },
  methods: {
    loadData(year) {
      const options = {
        method: "GET",
        url: "https://live-golf-data.p.rapidapi.com/stats",
        params: { year: this.year, statId: "186" },
        headers: {
          "X-RapidAPI-Key": "465345ef14msh34d80558d96c789p1e003cjsn4083f5b96d40",
          "X-RapidAPI-Host": "live-golf-data.p.rapidapi.com",
        },
      };
      axios
        .request(options)
        .then((response) => {
          this.players = response.data.rankings.slice(0, 100);
          this.getDataPages(1);
        })
        .catch((err) => {
          console.log(err);
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
    totalPages() {
      return Math.ceil(this.players.length / this.elementsPerPage);
    },
    getDataPages(numberPage) {
      this.actualPage = numberPage;

      let ini = numberPage * this.elementsPerPage - this.elementsPerPage;
      let end = numberPage * this.elementsPerPage;

      this.paginatedData = this.players.slice(ini, end);
    },
    getPreviousPage() {
      if (this.actualPage > 1) {
        this.actualPage--;
      }
      this.getDataPages(this.actualPage);
    },
    getNextPage() {
      if (this.actualPage < this.totalPages()) {
        this.actualPage++;
      }
      this.getDataPages(this.actualPage);
    },
    isActivePage(numberPage) {
      return numberPage == this.actualPage ? "active-pagination" : "";
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
            location.href = "index.html";
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    parsePoints(points) {
      if (this.year == 2022) {
        return points.toFixed(2);
      } else {
        return points;
      }
    },
  },
  computed: {},
}).mount("#app");
