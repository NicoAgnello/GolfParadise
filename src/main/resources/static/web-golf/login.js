const { createApp } = Vue
const useVuelidate  = Vuelidate.useVuelidate
const required      = VuelidateValidators.required
const email         = VuelidateValidators.email
const minLength     = VuelidateValidators.minLength

createApp({
    data() {
        return {
            container:            document.querySelector(".container"),
            v$:                   useVuelidate(),
            email:                "",
            password:             "",
            newClientFirstName:   "",
            newClientLastName:    "",
            newClientEmail:       "",
            newClientPassword:    "",
            invalidCredentials:   false,
            emailInUse:           false
            // v$:                 useVuelidate(),
            // products:           [],
            // cart:               [],
            // total:              0,
            // deliveryCost:       0,
            // cardNumber:         "",
            // cardCvv:            "",
            // deliveryAddress:    "",
            // zipCode:            "",
            // stockInconsistency: false
        }
    },
    validations() {
        return {
            email:              { required, email },
            password:           { required },
            newClientFirstName: { required },
            newClientLastName:  { required },
            newClientEmail:     { required, email },
            newClientPassword:  { required, minLength: minLength(8) }
        }
    },
    created() {
        // this.loadData()
    },
    // validations() {
    //     return {
    //         cardNumber: {
    //             required,
    //             numeric,
    //             minLength: minLength(16)
    //         },
    //         cardCvv: {
    //             required,
    //             numeric,
    //             minLength: minLength(3)
    //         },
    //         deliveryAddress: {
    //             required,
    //         },
    //         zipCode: {
    //             required,
    //             numeric,
    //             minLength: minLength(3)
    //         }
    //     }
    // },
    methods: {
        signUpToggle() {
            this.container.classList.add("sign-up-mode");
        },
        signInToggle() {
            this.container.classList.remove("sign-up-mode");
        },
        logUser() {
            axios.post('/api/login',
                `email=${this.email}&password=${this.password}`,
                {
                    headers: {
                        'content-type':'application/x-www-form-urlencoded'
                    }
                })
                .then(response => {
                    console.log(response)
                    // if (this.email === "admin@admin.com") {
                    //     location.replace("/manager/manager.html")
                    // } else {
                    //     location.replace("/web/accounts.html")
                    // }
                })
                .catch(error => {
                    console.log(error.message)
                    this.invalidCredentials = true
                })
        },
        registerUser() {
            axios.post('/api/clients',
                `firstName=${this.newClientFirstName}&lastName=${this.newClientLastName}&email=${this.newClientEmail}&password=${this.newClientPassword}`,
                {
                    headers:
                        {
                            'content-type':'application/x-www-form-urlencoded'
                        }
                })
                .then(() => {
                    this.email    = this.newClientEmail
                    this.password = this.newClientPassword
                    this.logUser()
                })
                .catch(error => {
                    console.log(error.response.data)
                    if (error.response.data === "Email already in use") {
                        this.emailInUse = true
                    }
                })
        },
        submitLoginForm(e) {
            e.preventDefault()
            this.v$.email.$touch();
            this.v$.password.$touch();
            if (!this.v$.email.$invalid && !this.v$.password.$invalid) {
                this.logUser()
            }
        },
        submitSignUpForm(e) {
            e.preventDefault()
            this.v$.newClientFirstName.$touch();
            this.v$.newClientLastName.$touch();
            this.v$.newClientEmail.$touch();
            this.v$.newClientPassword.$touch();
            if (!this.v$.newClientFirstName.$invalid &&
                !this.v$.newClientLastName.$invalid &&
                !this.v$.newClientEmail.$invalid &&
                !this.v$.newClientPassword.$invalid) {
                this.registerUser()
            }
        },
    //     loadData() {
    //         if (localStorage.getItem('cart') != null) {
    //             this.cart       = JSON.parse(localStorage.getItem('cart'))
    //             this.total      = JSON.parse(localStorage.getItem('total'))
    //         }
    //         axios('/api/products')
    //             .then(response => {
    //                 console.log(response)
    //                 this.products = response.data
    //                 this.cart.forEach(order => {
    //                     this.products.forEach(prod => {
    //                         if (prod.id == order.id) {
    //                             order.price = prod.price
    //                             if (prod.stock - order.quantity >= 0 ) {
    //                                 order.stock = prod.stock - order.quantity
    //                             } else {
    //                                 this.stockInconsistency = true
    //                             }
    //                         }
    //                     })
    //                 })
    //                 if (this.stockInconsistency) {
    //                     console.log('Stock inconsistency.')
    //                     localStorage.clear()
    //                     Swal.fire({
    //                         showConfirmButton: false,
    //                         timer:            2000,
    //                         timerProgressBar: true,
    //                         icon:             'error',
    //                         title:            `Stock inconsistency`,
    //                     })
    //                     setTimeout(() => location.replace("/web-golf/mockup.html"),2000)
    //                 }
    //             })
    //             .catch(error => console.log(error))
    //
    //     },
    //     checkProductInCart(product) {
    //         return this.cart.find(prod => product.id === prod.id)
    //     },
    //     addProduct(product) {
    //         if (this.checkProductInCart(product)) {
    //             product.quantity++
    //         } else {
    //             this.cart.push(product)
    //             product.quantity = 1
    //         }
    //         product.stock--
    //         this.getTotal()
    //         localStorage.setItem('cart', JSON.stringify(this.cart))
    //         localStorage.setItem('total', JSON.stringify(this.total))
    //     },
    //     removeProductQuantity(product) {
    //         product.quantity--
    //         product.stock++
    //         if (product.quantity === 0) {
    //             this.cart.splice(this.cart.indexOf(product), 1)
    //         }
    //         this.getTotal()
    //         localStorage.setItem('cart', JSON.stringify(this.cart))
    //         localStorage.setItem('total', JSON.stringify(this.total))
    //     },
    //     removeProduct(product) {
    //         axios('/api/products')
    //             .then(response => {
    //                 console.log(response)
    //                 product.stock = response.data.find(prod => prod.id === product.id).stock
    //                 product.quantity = 0;
    //                 this.cart.splice(this.cart.indexOf(product), 1)
    //                 this.getTotal()
    //                 localStorage.setItem('cart', JSON.stringify(this.cart))
    //                 localStorage.setItem('total', JSON.stringify(this.total))
    //             })
    //             .catch(error => console.log(error))
    //     },
    //     emptyCart() {
    //         localStorage.clear()
    //         this.loadData()
    //         this.cart = []
    //         this.total = 0
    //     },
    //     getTotal(product) {
    //         this.total = this.cart.reduce((acc, product) => acc + Number(product.price * product.quantity), 0)
    //     },
    //     validateForm(e) {
    //         e.preventDefault()
    //         this.v$.cardNumber.$touch();
    //         this.v$.cardCvv.$touch();
    //         this.v$.deliveryAddress.$touch();
    //         this.v$.zipCode.$touch();
    //         if (!this.v$.cardNumber.$invalid
    //             && !this.v$.cardCvv.$invalid
    //             && !this.v$.deliveryAddress.$invalid
    //             && !this.v$.zipCode.$invalid) {
    //             this.pay()
    //         }
    //     },
    //     getDeliveryCost() {
    //         console.log(this.zipCode)
    //         axios.post('/api/deliveryCost', `zipCode=${this.zipCode}`)
    //             .then(response => {
    //                 this.deliveryCost = response.data.deliveryCost
    //             })
    //             .catch(error => console.log(error))
    //     },
    //     pay() {
    //         const orders = this.cart.reduce((acc, curr) => {
    //             const order = {
    //                 productId: curr.id,
    //                 quantity: curr.quantity
    //             }
    //             acc.push(order)
    //             return acc
    //         }, [])
    //         axios.post('https://mindhub-brothers.up.railway.app/api/cards/pay', {
    //             "cardNumber": this.cardNumber,
    //             "cardCvv": this.cardCvv,
    //             "amount":  this.total + this.deliveryCost,
    //             "description": "Golf Paradise purchase."
    //         })
    //             .then(response => {
    //                 console.log(response)
    //                 axios.post('/api/orderProducts/generate', {orderProducts: orders})
    //                     .then(response => {
    //                         console.log(response)
    //                         window.location.href = '/api/pdf/generate'
    //                         localStorage.clear()
    //                         Swal.fire({
    //                             showConfirmButton: false,
    //                             timer:            2000,
    //                             timerProgressBar: true,
    //                             icon:             'success',
    //                             title:            `Purchase successful`,
    //                             image:            "./assets/swal-image.jpg",
    //                             imageWidth:       "100%",
    //                             imageHeight:      200,
    //                             imageAlt:         'Golfer',
    //                         })
    //                         setTimeout(() => location.replace("/web-golf/mockup.html"),2000)
    //                     })
    //                     .catch(error => console.log(error))
    //             })
    //             .catch(error => console.log(error))
    //     }
    }
}).mount('#app')