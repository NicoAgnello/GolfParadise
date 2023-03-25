const { createApp } = Vue

createApp({
    data() {
        return {
            products:           [],
            cart:               [],
            total:              0,
            stockInconsistency: false
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            if (localStorage.getItem('cart') != null) {
                this.cart       = JSON.parse(localStorage.getItem('cart'))
                this.total      = JSON.parse(localStorage.getItem('total'))
            }
            axios('/api/products')
                .then(response => {
                    console.log(response)
                    this.products = response.data
                    this.cart.forEach(order => {
                        this.products.forEach(prod => {
                            if (prod.id == order.id) {
                                order.price = prod.price
                                if (prod.stock - order.quantity >= 0 ) {
                                    order.stock = prod.stock - order.quantity
                                } else {
                                    this.stockInconsistency = true
                                }
                            }
                        })
                    })
                    if (this.stockInconsistency) {
                        console.log('Stock inconsistency.')
                        localStorage.clear()
                        Swal.fire({
                            showConfirmButton: false,
                            timer:            2000,
                            timerProgressBar: true,
                            icon:             'error',
                            title:            `Stock inconsistency`,
                        })
                        setTimeout(() => location.replace("/web-golf/mockup.html"),2000)
                    }
                })
                .catch(error => console.log(error))

        },
        checkProductInCart(product) {
            return this.cart.find(prod => product.id === prod.id)
        },
        addProduct(product) {
            if (this.checkProductInCart(product)) {
                product.quantity++
            } else {
                this.cart.push(product)
                product.quantity = 1
            }
            product.stock--
            this.getTotal()
            localStorage.setItem('cart', JSON.stringify(this.cart))
            localStorage.setItem('total', JSON.stringify(this.total))
        },
        removeProductQuantity(product) {
            product.quantity--
            product.stock++
            if (product.quantity === 0) {
                this.cart.splice(this.cart.indexOf(product), 1)
            }
            this.getTotal()
            localStorage.setItem('cart', JSON.stringify(this.cart))
            localStorage.setItem('total', JSON.stringify(this.total))
        },
        removeProduct(product) {
            axios('/api/products')
                .then(response => {
                    console.log(response)
                    product.stock = response.data.find(prod => prod.id === product.id).stock
                    product.quantity = 0;
                    this.cart.splice(this.cart.indexOf(product), 1)
                    this.getTotal()
                    localStorage.setItem('cart', JSON.stringify(this.cart))
                    localStorage.setItem('total', JSON.stringify(this.total))
                })
                .catch(error => console.log(error))
        },
        emptyCart() {
            localStorage.clear()
            this.loadData()
            this.cart = []
            this.total = 0
        },
        getTotal(product) {
            this.total = this.cart.reduce((acc, product) => acc + Number(product.price * product.quantity), 0)
        },
        pay() {
            const orders = this.cart.reduce((acc, curr) => {
                const order = {
                    productId: curr.id,
                    quantity: curr.quantity
                }
                acc.push(order)
                return acc
            }, [])
            axios.post('/api/orderProducts/generate', {orderProducts: orders})
                .then(response => {
                    console.log(response)
                    window.location.href = '/api/pdf/generate'
                })
                .catch(error => console.log(error))
        }
    }
}).mount('#app')