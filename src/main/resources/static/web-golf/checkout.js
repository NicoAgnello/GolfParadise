const { createApp } = Vue;
const useVuelidate = Vuelidate.useVuelidate;
const required = VuelidateValidators.required;
const minLength = VuelidateValidators.minLength;
const numeric = VuelidateValidators.numeric;

createApp({
  data() {
    return {
      v$: useVuelidate(),
      products: [],
      cart: [],
      total: 0,
      deliveryCost: 0,
      cardNumber: "",
      cardCvv: "",
      deliveryAddress: "",
      zipCode: "",
      stockInconsistency: false,
    };
  },
  created() {
    this.loadData();
  },
  validations() {
    return {
      cardNumber: {
        required,
        numeric,
        minLength: minLength(16),
      },
      cardCvv: {
        required,
        numeric,
        minLength: minLength(3),
      },
      deliveryAddress: {
        required,
      },
      zipCode: {
        required,
        numeric,
        minLength: minLength(3),
      },
    };
  },
  methods: {
    loadData() {
      if (localStorage.getItem("cart") != null) {
        this.cart = JSON.parse(localStorage.getItem("cart"));
        this.total = JSON.parse(localStorage.getItem("total"));
      }
      axios("/api/products")
          .then((response) => {
            console.log(response);
            this.products = response.data;
            this.cart.forEach((order) => {
              this.products.forEach((prod) => {
                if (prod.id == order.id) {
                  order.price = prod.price;
                  if (prod.stock - order.quantity >= 0) {
                    order.stock = prod.stock - order.quantity;
                  } else {
                    this.stockInconsistency = true;
                  }
                }
              });
            });
            if (this.stockInconsistency) {
              console.log("Stock inconsistency.");
              localStorage.clear();
              Swal.fire({
                showConfirmButton: false,
                timer: 2000,
                timerProgressBar: true,
                icon: "error",
                title: `Stock inconsistency`,
              });
              setTimeout(() => location.replace("/web-golf/mockup.html"), 2000);
            }
          })
          .catch((error) => console.log(error));
    },
    checkProductInCart(product) {
      return this.cart.find((prod) => product.id === prod.id);
    },
    addProduct(product) {
      if (this.checkProductInCart(product)) {
        product.quantity++;
      } else {
        this.cart.push(product);
        product.quantity = 1;
      }
      product.stock--;
      this.getTotal();
      localStorage.setItem("cart", JSON.stringify(this.cart));
      localStorage.setItem("total", JSON.stringify(this.total));
    },
    removeProductQuantity(product) {
      product.quantity--;
      product.stock++;
      if (product.quantity === 0) {
        this.cart.splice(this.cart.indexOf(product), 1);
      }
      this.getTotal();
      localStorage.setItem("cart", JSON.stringify(this.cart));
      localStorage.setItem("total", JSON.stringify(this.total));
    },
    removeProduct(product) {
      axios("/api/products")
          .then((response) => {
            console.log(response);
            product.stock = response.data.find((prod) => prod.id === product.id).stock;
            product.quantity = 0;
            this.cart.splice(this.cart.indexOf(product), 1);
            this.getTotal();
            localStorage.setItem("cart", JSON.stringify(this.cart));
            localStorage.setItem("total", JSON.stringify(this.total));
          })
          .catch((error) => console.log(error));
    },
    emptyCart() {
      localStorage.clear();
      this.loadData();
      this.cart = [];
      this.total = 0;
    },
    getTotal(product) {
      this.total = this.cart.reduce((acc, product) => acc + Number(product.price * product.quantity), 0);
    },
    validateForm(e) {
      e.preventDefault();
      this.v$.cardNumber.$touch();
      this.v$.cardCvv.$touch();
      this.v$.deliveryAddress.$touch();
      this.v$.zipCode.$touch();
      if (
          !this.v$.cardNumber.$invalid &&
          !this.v$.cardCvv.$invalid &&
          !this.v$.deliveryAddress.$invalid &&
          !this.v$.zipCode.$invalid
      ) {
        this.pay();
      }
    },
    getDeliveryCost() {
      if (this.zipCode !== "") {
        axios.post("/api/clients/current/delivery-cost", `zipCode=${this.zipCode}`)
            .then((response) => {
              this.deliveryCost = response.data.deliveryCost;
            })
            .catch((error) => console.log(error));
      }
    },
    pay() {
      const orders = this.cart.reduce((acc, curr) => {
        const order = {
          productId: curr.id,
          quantity: curr.quantity,
        };
        acc.push(order);
        return acc;
      }, []);
      axios
          .post("https://mindhub-brothers.up.railway.app/api/cards/pay", {
            cardNumber: this.cardNumber,
            cardCvv: this.cardCvv,
            amount: this.total + this.deliveryCost,
            description: "Golf Paradise purchase.",
          })
          .then((response) => {
            console.log(response);
            axios
                .post("/api/clients/current/order-purchases/generate", { orderProducts: orders, deliveryAddress: this.deliveryAddress, deliveryCost: this.deliveryCost })
                .then((response) => {
                  console.log(response);
                  window.location.href = "/api/clients/current/pdf/generate";
                  localStorage.clear();
                  Swal.fire({
                    showConfirmButton: false,
                    timer: 2000,
                    timerProgressBar: true,
                    icon: "success",
                    background: "#FCE6BE",
                    title: `Purchase successful`,
                  });
                  setTimeout(() => location.replace("/web-golf/contact.html"), 2000);
                })
                .catch((error) => console.log(error));
          })
          .catch((error) => {
            console.log(error);
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: error.response.data,
              background: "#FCE6BE",
              confirmButtonColor: "#198754",
            });
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
              location.href = "index.html";
            });
          })
          .catch((err) => {
            console.log(err);
          });
    },
  },
}).mount("#app");
