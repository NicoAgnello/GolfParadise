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
    };
  },
  created() {},
  methods: {
    newProduct() {
      Swal.fire({
        title: "New Product",
        html:
          `<input type="text" id="swal-input1" class="swal2-input" placeholder="Name of product" > ` +
          '<input type="text" id="swal-input2" class="swal2-input" placeholder="Description">' +
          '<select  id="swal-input3" class="swal2-input" name="category" style="width: 18rem">' +
          '<option value="" disabled selected >Category</option>' +
          '<option value="clubs"> Clubs </option> ' +
          '<option value="bags"> Bags </option> ' +
          '<option value="balls"> Balls </option> ' +
          '<option value="clothes"> Clothes </option> ' +
          '<option value="accesories"> Accesories </option> ' +
          '<option value="shoes"> Shoes </option> ' +
          '<input v-model="newProductUrl"  type="text" id="swal-input4" class="swal2-input" placeholder="Image URL">' +
          '<input v-model="newProductStock"  type="number" id="swal-input5" class="swal2-input" placeholder="Stock">' +
          '<input v-model="newProductPrice"  type="number" id="swal-input6" class="swal2-input" placeholder="Price">',
        focusConfirm: false,
        background: "#FCE6BE",
        confirmButtonColor: "#598526",
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
      })
        .then((result) => {
          if (result && result.value) {
            this.newProductName = result.value[0];
            this.newProductDescription = result.value[1];
            this.newProductCategory = result.value[2];
            this.newProductUrl = result.value[3];
            this.newProductStock = result.value[4];
            this.newProductPrice = result.value[5];
          }
        })
        .then(() => {
          console.log(this.newProductName);
          console.log(this.newProductDescription);
          console.log(this.newProductCategory);
          console.log(this.newProductUrl);
          console.log(this.newProductStock);
          console.log(this.newProductPrice);
        });
    },
  },
}).mount("#app");