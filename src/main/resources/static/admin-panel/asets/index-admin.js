const { createApp } = Vue;

createApp({
  data() {
    return {
      newProductName: "",
      newProductDescription: "",
      newProductCategory: "",
      newProductUrl: "",
      newProductStock: "",
      newProductPrice: "",
      elementsPerPage: 10,
      products: [],
      paginatedData: [],
      actualPage: 1,
      categories: [],
      categoryToFilter: "",
      filteredProducts: [],
      searchValue: "",
      description: "",
      stock: "",
    };
  },
  created() {
    this.getProduts();
  },
  mounted() {},
  methods: {
    getProduts() {
      axios
        .get("/api/products")
        .then((res) => {
          this.products = res.data.filter((product) => product.active);
          this.filteredProducts = [...this.products];
          this.categories = [...new Set(this.products.map((prod) => prod.category))];
          this.getDataPages(1);
        })
        .catch((err) => console.log(err));
    },
    getUrl(url) {
      Swal.fire({
        title: "URL address",
        input: "url",
        inputValue: url,
        showConfirmButton: false,
      });
    },

    newProduct() {
      Swal.fire({
        title: "New Product",
        html:
          `<input type="text" id="swal-input1" class="swal2-input" placeholder="Name of product" > ` +
          '<textarea type="text" id="swal-input2" class="swal2-input" placeholder="Description"></textarea>' +
          '<select  id="swal-input3" class="swal2-input" name="category" style="width: 18rem">' +
          '<option value="" disabled selected >Category</option>' +
          '<option value="CLUBS"> Clubs </option> ' +
          '<option value="BAGS"> Bags </option> ' +
          '<option value="BALLS"> Balls </option> ' +
          '<option value="CLOTHES"> Clothes </option> ' +
          '<option value="ACCESORIES"> Accesories </option> ' +
          '<option value="SHOES"> Shoes </option> ' +
          '<input v-model="newProductUrl"  type="text" id="swal-input4" class="swal2-input" placeholder="Image URL">' +
          '<input v-model="newProductStock"  type="number" id="swal-input5" class="swal2-input" placeholder="Stock">' +
          '<input v-model="newProductPrice"  type="number" id="swal-input6" class="swal2-input" placeholder="Price">',
        focusConfirm: false,
        background: "#FCE6BE",
        confirmButtonColor: "#198754",
        showCancelButton: true,
        cancelButtonColor: "#901",
        preConfirm: () => {
          return [
            document.getElementById("swal-input1").value,
            document.getElementById("swal-input2").value,
            document.getElementById("swal-input3").value,
            document.getElementById("swal-input4").value,
            document.getElementById("swal-input5").value,
            document.getElementById("swal-input6").value,
          ];
        },
      }).then((result) => {
        if (result && result.value) {
          this.newProductName = result.value[0];
          this.newProductDescription = result.value[1];
          this.newProductCategory = result.value[2];
          this.newProductUrl = result.value[3];
          this.newProductStock = result.value[4];
          this.newProductPrice = result.value[5];

          if (this.newProductCategory !== "" && this.newProductStock !== "" && this.newProductPrice !== "") {
            axios
              .post(
                `/api/products`,
                `name=${this.newProductName}&description=${this.newProductDescription}&img=${this.newProductUrl}&price=${this.newProductPrice}&stock=${this.newProductStock}&category=${this.newProductCategory}`
              )
              .then(() => {
                Swal.fire({
                  icon: "success",
                  title: "New product has been added",
                  background: "#FCE6BE",
                  confirmButtonColor: "#198754",
                });
                this.getProduts();
              })
              .catch((err) => {
                Swal.fire({
                  position: "center",
                  icon: "error",
                  background: "#FCE6BE",
                  confirmButtonColor: "#198754",
                  title: err.response.data,
                  showConfirmButton: true,
                });
              });
          } else {
            Swal.fire({
              position: "center",
              icon: "error",
              background: "#FCE6BE",
              confirmButtonColor: "#198754",
              title: "Missing Data",
              showConfirmButton: true,
            });
          }
        }
      });
    },
    setStock(productId, stock) {
      Swal.fire({
        title: "Set stock of product",
        input: "range",
        inputAttributes: {
          min: 0,
          max: 500,
          step: 1,
        },
        inputValue: stock,
        background: "#FCE6BE",
        confirmButtonColor: "#198754",
      }).then((result) => {
        if (result.isConfirmed) {
          this.stock = result.value;
          axios
            .patch("/api/products/stock", `id=${productId}&stock=${this.stock}`)
            .then(() => {
              Swal.fire({
                icon: "success",
                title: "Stock updated",
                background: "#FCE6BE",
                confirmButtonColor: "#198754",
              }).then(() => {
                this.getProduts();
              });
            })
            .catch((err) => console.log(err));
        }
      });
    },
    deleteProduct(productId) {
      Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#198754",
        cancelButtonColor: "#d33",
        background: "#FCE6BE",
        confirmButtonText: "Yes, delete it!",
      }).then((result) => {
        if (result.isConfirmed) {
          axios
            .patch(`/api/products/${productId}`)
            .then(() => {
              Swal.fire({
                position: "center",
                icon: "success",
                title: "Product has been deleted",
                showConfirmButton: true,
                background: "#FCE6BE",
                confirmButtonColor: "#198754",
              });
            })
            .catch((err) => {
              Swal.fire({
                position: "center",
                icon: "error",
                background: "#FCE6BE",
                confirmButtonColor: "#198754",
                title: err.response.data,
                showConfirmButton: true,
              });
            })
            .then(() => {
              this.getProduts();
            });
        }
      });
    },
    totalPages() {
      return Math.ceil(this.products.length / this.elementsPerPage);
    },
    getDataPages(numberPage) {
      this.actualPage = numberPage;

      let ini = numberPage * this.elementsPerPage - this.elementsPerPage;
      let end = numberPage * this.elementsPerPage;

      this.paginatedData = this.filteredProducts.slice(ini, end);
    },
    getPreviousPage() {
      if (this.actualPage > 1) {
        this.actualPage--;
      }
      this.getDataPages(actualPage);
    },
    getNextPage() {
      if (this.actualPage < this.totalPages()) {
        this.actualPage++;
      }
      this.getDataPages(actualPage);
    },
    isActivePage(numberPage) {
      return numberPage == this.actualPage ? "active-pagination" : "";
    },
    searchByText() {
      let filteredProducts = this.products.filter((product) =>
        product.name.toLowerCase().includes(this.searchValue.toLowerCase())
      );
      return filteredProducts;
    },
    filterByCategory(products) {
      let filteredProducts = products.filter((product) => product.category == this.categoryToFilter);
      return filteredProducts;
    },
    crossFilter() {
      const filterProductsBySearch = this.searchByText();
      const filterProductsByCategory = this.filterByCategory(filterProductsBySearch);
      if (this.categoryToFilter == "") {
        this.filteredProducts = filterProductsBySearch;
      } else if (filterProductsByCategory.length === 0) {
        this.filteredProducts = null;
      } else {
        this.filteredProducts = filterProductsByCategory;
      }
      // this.getDataPages(1);
    },
    clearFilter() {
      this.categoryToFilter = "";
      this.searchValue = "";
      this.crossFilter();
    },
    parseDescription(description) {
      return description.slice(0, 20);
    },
    showDescription(description) {
      Swal.fire({
        text: description,
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
          });
        })
        .then(() => {
          // location.href =
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
}).mount("#app");
