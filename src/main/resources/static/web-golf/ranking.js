const { createApp } = Vue;

createApp({
  data() {
    return {
      players: [],
      year: 2022,
      elementsPerPage: 15,
      paginatedData: [],
      actualPage: 1,
    };
  },
  created() {
    this.loadData(this.year);
  },
  methods: {
    loadData(year) {
      const options = {
        method: "GET",
        url: "https://live-golf-data.p.rapidapi.com/stats",
        params: { year: this.year, statId: "186" },
        headers: {
          "X-RapidAPI-Key": "14845836c6mshde34cb9c46021b2p1913a2jsn7de82339e6e3",
          "X-RapidAPI-Host": "live-golf-data.p.rapidapi.com",
        },
      };
      axios
        .request(options)
        .then((response) => {
          this.players = response.data.rankings.slice(0, 100);
          this.getDataPages(1);
          //   console.log(this.players);
        })
        .catch((err) => {
          console.log(err);
        });
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
            location.href = "./landing.html";
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  computed: {},
}).mount("#App");
