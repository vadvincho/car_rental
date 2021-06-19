const urlUsers = 'http://localhost:8081/users'
const urlLogin = 'http://localhost:8081/login'
const urlLogout = 'http://localhost:8081/logout2'
const urlCars = 'http://localhost:8081/cars'
const urlOrders = 'http://localhost:8081/orders'
const urlOrdersCancel = 'http://localhost:8081/orders/cancel'
const urlOrdersConfirm = 'http://localhost:8081/orders/confirm'
const urlOrdersComplete = 'http://localhost:8081/orders/complete'
const urlCustomers = 'http://localhost:8081/customers'
const urlCustomersTopUpBalance = 'http://localhost:8081/customers/top-up-balance'

const authName = document.querySelector('.auth-name');
const btnLogin = document.querySelector('.btn-login');
const btnRegister = document.querySelector('.btn-register');
const btnLogout = document.querySelector('.btn-logout');
const loginModal = document.querySelector('.login-modal');
const loginModalForm = loginModal.querySelector('.form');
const registerModal = document.querySelector('.register-modal');
const registerModalForm = registerModal.querySelector('.form');

const post = function (url, data) {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: data
    })
        // .then(res => {
        //     if (res.status == 403) {
        //         alert("No access rights");
        //     } else {
        //         res.json().then(json => alert(json.message)).then(() => location.reload());
        //     }
        // })
        .then(res => res.json())
        .then(json => alert(json.message))
        .then(() => location.reload())
}

const get = function (url) {
    fetch(url, {
        method: 'GET'
    })
        .then(res => {
            if (res.status == 403) {
                alert("No access rights");
            } else {
                res.json().then(json => alert(json.message)).then(() => location.reload());
            }
        })
    // .then(res => res.json())
    // .then(json => alert(json.message))
    // .then(() => location.reload())
}

// register
if (btnRegister) {
    btnRegister.addEventListener('click', () => {
        registerModal.classList.add('modal-show');
        registerModalForm.login.value = '';
        registerModalForm.password.value = '';
        registerModalForm.confirmPassword.value = '';
        registerModalForm.email.value = '';
    });
}

registerModalForm.addEventListener('submit', e => {
    e.preventDefault();
    let errors = registerModalForm.querySelectorAll('.error');
    for (let i = 0; i < errors.length; i++) {
        errors[i].remove()
    }
    let password = registerModalForm.password;
    let cPassword = registerModalForm.confirmPassword;
    if (password.value !== cPassword.value) {
        console.log('Password and password confirmation not equals')
        let error = document.createElement('div')
        error.className = 'error'
        error.style.color = 'red'
        error.innerHTML = 'Passwords doesnt match'
        password.parentElement.insertBefore(error, password)
    } else {
        var dataUser = JSON.stringify({
            login: registerModalForm.login.value,
            password: registerModalForm.password.value,
            email: registerModalForm.email.value
        });
        post(urlUsers,
            dataUser);
        registerModal.classList.remove('modal-show');
    }
});
loginModalForm.addEventListener('submit', e => {
    e.preventDefault();
    post(urlLogin,
        JSON.stringify({
            login: loginModalForm.login.value,
            password: loginModalForm.password.value
        }));
    loginModal.classList.remove('modal-show');
});

if (btnLogin) {
    btnLogin.addEventListener('click', () => {
        loginModal.classList.add('modal-show');
        loginModalForm.login.value = '';
        loginModalForm.password.value = '';
    });
}
if (btnLogout) {
    btnLogout.addEventListener('click', () => {
        get(urlLogout);
    });
}

// User click anywhere outside the modal
window.addEventListener('click', e => {
    if (e.target === loginModal) {
        loginModal.classList.remove('modal-show');
    }
    if (e.target === registerModal) {
        registerModal.classList.remove('modal-show');
    }
    if (e.target === orderCompleteModal) {
        orderCompleteModal.classList.remove('modal-show');
    }
    if (e.target === orderCancelModal) {
        orderCancelModal.classList.remove('modal-show');
    }
    if (e.target === topBalanceModal) {
        topBalanceModal.classList.remove('modal-show');
    }
    if (e.target === topBalanceModal) {
        topBalanceModal.classList.remove('modal-show');
    }
});


//tab profile
const tpProfile = document.querySelector('.tp-profile');
const topBalanceModal = document.querySelector('.top-balance-modal');
if (tpProfile) {
    let userId;
    let customerId;
    const tpProfileForm = tpProfile.querySelector('.form');
    const inputProfile = document.querySelector('.input-profile');
    const btnFormEdit = tpProfile.querySelector('.btn-edit');
    const btnFormSaveChange = tpProfile.querySelector('.btn-save-change');
    const btnTopBalance = tpProfile.querySelector('.btn-top-balance');

    const inputBalanceModal = topBalanceModal.querySelector('.balance-price');

    if (btnTopBalance) {
        btnTopBalance.addEventListener('click', () => {
            topBalanceModal.classList.add('modal-show');
        });
        topBalanceModal.addEventListener('submit', (e) => {
            e.preventDefault();
            fetch(`${urlCustomersTopUpBalance}/${customerId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    money: inputBalanceModal.value
                })
            })
                .then(res => {
                    if (res.status == 403) {
                        alert("No access rights");
                    } else {
                        res.json().then(json => alert(json.message));
                    }
                })
                // .then(res => res.json())
                // .then(json => {
                //     alert(json.message);
                // })
                .then(() => location.reload())
            topBalanceModal.classList.remove('modal-show');
        });
    }

    inputProfile.addEventListener('click', () => {
        fetch(`${urlUsers}/bylogin/${authName.innerHTML}`, {
            method: 'GET'
        })
            // .then(res => {
            //     if (res.status == 403) {
            //         alert("No access rights");
            //     } else {
            //         res.json().then(json => {
            //             userId = json.id;
            //             tpProfileForm.login.value = json.login;
            //             tpProfileForm.email.value = json.email;
            //             tpProfileForm.role.value = json.role.name;
            //         })
            //     }
            // })
            .then(res => res.json())
            .then(json => {
                userId = json.id;
                tpProfileForm.login.value = json.login;
                tpProfileForm.email.value = json.email;
                tpProfileForm.role.value = json.role.name;
            })
            .then(() => {
                if (tpProfileForm.name) {
                    fetch(`${urlCustomers}/byUser/${userId}`, {
                        method: 'GET'
                    })
                        .then(res => res.json())
                        .then(json => {
                            customerId = json.id;
                            tpProfileForm.name.value = json.name;
                            tpProfileForm.phoneNumber.value = json.phoneNumber;
                            tpProfileForm.passport.value = json.passport;
                            tpProfileForm.balance.value = json.balance;

                        })
                    // .then(res => {
                    //     if (res.status == 403) {
                    //         alert("No access rights");
                    //     } else {
                    //         res.json().then(json => {
                    //             customerId = json.id;
                    //             if (tpProfileForm.name) {
                    //                 tpProfileForm.name.value = json.name;
                    //                 tpProfileForm.phoneNumber.value = json.phoneNumber;
                    //                 tpProfileForm.passport.value = json.passport;
                    //                 tpProfileForm.balance.value = json.balance;
                    //             }
                    //         });
                    //     }
                    // })
                }
            })
    });


    btnFormEdit.addEventListener('click', () => {
        tpProfileForm.login.removeAttribute("disabled");
        tpProfileForm.email.removeAttribute("disabled");
        if (tpProfileForm.name) {
            tpProfileForm.name.removeAttribute("disabled");
            tpProfileForm.phoneNumber.removeAttribute("disabled");
            tpProfileForm.passport.removeAttribute("disabled");
        }
        btnFormSaveChange.removeAttribute("hidden");

        btnFormSaveChange.addEventListener("click", () => {
            fetch(urlUsers, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: userId,
                    login: tpProfileForm.login.value,
                    email: tpProfileForm.email.value,
                })
            })
                .then(res => {
                    if (res.status == 403) {
                        alert("No access rights");
                    } else {
                        res.json().then(json => {
                            alert(json.message);
                            tpProfileForm.login.setAttribute("disabled", "disabled");
                            tpProfileForm.email.setAttribute("disabled", "disabled");
                        });
                    }
                })
            // .then(res => res.json())
            // .then(json => {
            //     alert(json.message);
            //     tpProfileForm.login.setAttribute("disabled", "disabled");
            //     tpProfileForm.email.setAttribute("disabled", "disabled");
            // });

            if (tpProfileForm.name) {
                fetch(urlCustomers, {
                    method: 'PATCH',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        id: customerId,
                        name: tpProfileForm.name.value,
                        phoneNumber: tpProfileForm.phoneNumber.value,
                        passport: tpProfileForm.passport.value
                    })
                })
                    .then(res => {
                        if (res.status == 403) {
                            alert("No access rights");
                        } else {
                            res.json().then(json => {
                                tpProfileForm.name.setAttribute("disabled", "disabled");
                                tpProfileForm.phoneNumber.setAttribute("disabled", "disabled");
                                tpProfileForm.passport.setAttribute("disabled", "disabled");
                                btnFormSaveChange.setAttribute("hidden", "hidden");
                            });
                        }
                    })
                // .then(res => res.json())
                // .then(json => {
                //     tpProfileForm.name.setAttribute("disabled", "disabled");
                //     tpProfileForm.phoneNumber.setAttribute("disabled", "disabled");
                //     tpProfileForm.passport.setAttribute("disabled", "disabled");
                //     btnFormSaveChange.setAttribute("hidden", "hidden");
                // })
            }

        });
    })
}

//tab cars
const tpCars = document.querySelector('.tp-cars');
const editCarModal = document.querySelector('.edit-car-modal');
const editCarModalForm = editCarModal.querySelector('.form');
if (tpCars) {
    const tpCarsTable = tpCars.querySelector('.table-cars');
    const btnGetAvailableCars = tpCars.querySelector('.btn-get-available');
    const btnGetAllCars = tpCars.querySelector('.btn-get-all');

    function renderCars(cars) {
        tpCarsTable.innerHTML = '';
        const trth = `<tr>
                   <th>Car Id</th>
                   <th>Make</th>
                    <th>Model</th>
                    <th>Status</th>
                    <th>Action</th>
               </tr>`;
        tpCarsTable.insertAdjacentHTML('afterbegin', trth);
        let id;
        for (let i in cars) {
            const tr = `
    <tr class="tr-cars" data-id='${cars[i].id}'>
      <td>${cars[i].id}</td>
      <td>${cars[i].carModel.carMake}</td>
      <td>${cars[i].carModel.name}</td>
      <td>${cars[i].carStatus.status}</td>
      <td>
<!--        <button class="btn btn-edit">Edit</button>-->
        <button class="btn btn-delete">Delete</button>
      </td>
    </tr>
  `;
            tpCarsTable.insertAdjacentHTML('beforeend', tr);

            // Click delete car
            const btnDelete = document.querySelector(`[data-id='${cars[i].id}'] .btn-delete`);
            btnDelete.addEventListener('click', () => {
                fetch(`${urlCars}/${cars[i].id}`, {
                    method: 'DELETE'
                })
                    .then(res => {
                        if (res.status == 403) {
                            alert("No access rights");
                        } else {
                            res.json().then(json => {
                                alert(json.message)
                            }).then(() => location.reload());
                        }
                    })
                // .then(res => res.json())
                // .then(json => alert(json.message))

            });
        }
    }

    btnGetAvailableCars.addEventListener('click', () => {
        let resStatus;
        fetch(urlCars + "/status-available", {
            method: 'GET'
        })
            .then(res => {
                if (res.status == 403) {
                    alert("No access rights");
                } else {
                    res.json().then(json => renderCars(json));
                }
            })
    })
    btnGetAllCars.addEventListener('click', () => {
        fetch(urlCars, {
            method: 'GET'
        })
            .then(res => {
                if (res.status == 403) {
                    alert("No access rights");
                } else {
                    res.json().then(json => renderCars(json));
                }
            })
    })
}

const tpOrders = document.querySelector(".tp-orders");
if (tpOrders) {
    var id;
    const inStDate = document.querySelector('.input-start-date');
    const inEndDate = document.querySelector('.input-end-date');
    const inputOrders = document.querySelector('.input-orders');
    const btnOrderSubmit = document.querySelector('.btn-order-submit');
    const tpOrdersForm = tpOrders.querySelector(".form")
    var selectCars = document.getElementById("car-sel");
    var selectedIdCar;
    inputOrders.addEventListener("click", () => {
        fetch(urlCars + "/status-available", {
            method: 'GET'
        })
            .then(res => {
                if (res.status == 403) {
                    alert("No access rights");
                } else {
                    res.json().then(json => {
                        for (let i in json) {
                            selectCars.options[i] = new Option(json[i].carModel.carMake + " " + json[i].carModel.name, json[i].id);
                        }
                    });
                }
            })
            // .then(res => res.json())
            // .then(json => {
            //     for (let i in json) {
            //         selectCars.options[i] = new Option(json[i].carModel.carMake + " " + json[i].carModel.name, json[i].id);
            //     }
            // })
            .then(selectedIdCar = selectCars.value)
    });

    selectCars.addEventListener("change", () => {
        selectedIdCar = selectCars.value;
    });


    btnOrderSubmit.addEventListener("click", () => {
        fetch(`${urlUsers}/bylogin/${authName.innerHTML}`, {
            method: 'GET'
        })
            // .then(res => {
            //     if (res.status == 403) {
            //         alert("No access rights");
            //     } else {
            //         res.json().then(json => {
            //             console.log(json);
            //             var stDate = inStDate.value.split(".").reverse();
            //             stDate = stDate.map(function (item) {
            //                 return +item;
            //             });
            //             var endDate = inEndDate.value.split(".").reverse();
            //             endDate = endDate.map(function (item) {
            //                 return +item;
            //             });
            //             var order = JSON.stringify({
            //                 car: {id: selectedIdCar},
            //                 customer: {id: json.id},
            //                 startTime: stDate,
            //                 endTime: endDate
            //             });
            //             post(urlOrders, order);
            //         });
            //     }
            // })
            .then(res => res.json())
            .then(json => {
                let custId;
                fetch(`${urlCustomers}/byUser/${json.id}`, {
                    method: 'GET'
                }).then(rez => rez.json()).then(q => {
                    console.log(q);
                    var stDate = inStDate.value.split(".").reverse();
                    stDate = stDate.map(function (item) {
                        return +item;
                    });
                    var endDate = inEndDate.value.split(".").reverse();
                    endDate = endDate.map(function (item) {
                        return +item;
                    });
                    var order = JSON.stringify({
                        car: {id: selectedIdCar},
                        customer: {id: q.id},
                        startTime: stDate,
                        endTime: endDate
                    });
                    post(urlOrders, order);
                })
            })
    });
}

const tpOrdersAdmin = document.querySelector(".tp-orders-admin");
const orderCancelModal = document.querySelector(".order-cancel-modal");
const orderCompleteModal = document.querySelector(".order-complete-modal");
if (tpOrdersAdmin) {

    const inputOrderCancelModal = document.querySelector(".cancel-info");
    const inputDamageModal = document.querySelector(".damage-info");
    const inputPriceModal = document.querySelector(".price");
    const tpOrdersAdminTable = tpOrdersAdmin.querySelector('.table-orders');
    const inputOrdersAdmin = document.querySelector(".input-orders-admin");
    inputOrdersAdmin.addEventListener("click", () => {
        fetch(urlOrders, {
            method: 'GET'
        })
            .then(res => {
                if (res.status == 403) {
                    alert("No access rights");
                } else {
                    res.json().then(json => renderOrders(json));
                }
            })
        // .then(res => res.json())
        // .then(json => renderOrders(json))
    });

    function renderOrders(orders) {
        tpOrdersAdminTable.innerHTML = '';
        const trth = `<tr>
                   <th>Car</th>
                   <th>Customer</th>
                    <th>Status</th>
                    <th>Price</th>
                    <th>Action</th>
               </tr>`;
        tpOrdersAdminTable.insertAdjacentHTML('afterbegin', trth);
        let id;
        for (let i in orders) {
            const tr = `
    <tr data-id='${orders[i].id}'>
      <td>${orders[i].car.carModel.name + " " + orders[i].car.carModel.name}</td>
      <td>${orders[i].customer.name}</td>
      <td>${orders[i].orderStatus.status}</td>
      <td>${orders[i].price}</td>
      <td>
        <button class="btn btn-confirm">Confirm</button>
        <button class="btn btn-complete">Complete</button>
        <button class="btn btn-cancel">Cancel</button>
      </td>
    </tr>
  `;
            tpOrdersAdminTable.insertAdjacentHTML('beforeend', tr);

            // Click confirm order
            const btnConfirm = document.querySelector(`[data-id='${orders[i].id}'] .btn-confirm`);
            btnConfirm.addEventListener('click', () => {
                // editModal.classList.add('modal-show');
                fetch(`${urlOrdersConfirm}/${orders[i].id}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    // body: {}
                })
                    .then(res => {
                        if (res.status == 403) {
                            alert("No access rights");
                        } else {
                            res.json().then(json => alert(json.message));
                        }
                    })
                    // .then(res => res.json())
                    // .then(json => alert(json.message))
                    .then(() => location.reload())
            });

            // Click cancel order
            const btnCancel = document.querySelector(`[data-id='${orders[i].id}'] .btn-cancel`);
            btnCancel.addEventListener('click', () => {
                orderCancelModal.classList.add('modal-show');
                orderCancelModal.addEventListener('submit', e => {
                    e.preventDefault();
                    fetch(`${urlOrdersCancel}/${orders[i].id}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json'
                        },
                        body: JSON.stringify({
                            message: inputOrderCancelModal.value
                        })
                    })
                        .then(res => {
                            if (res.status == 403) {
                                alert("No access rights");
                            } else {
                                res.json().then(json => alert(json.message));
                            }
                        })
                        // .then(res => res.json())
                        // .then(json => alert(json.message))
                        .then(() => location.reload())
                    orderCancelModal.classList.remove('modal-show');
                });
            });

            // Click complete order
            const btnComplete = document.querySelector(`[data-id='${orders[i].id}'] .btn-complete`);
            btnComplete.addEventListener('click', () => {
                orderCompleteModal.classList.add('modal-show');
                orderCompleteModal.addEventListener('submit', e => {
                    e.preventDefault();
                    fetch(`${urlOrdersComplete}/${orders[i].id}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json'
                        },
                        body: JSON.stringify({
                            info: inputDamageModal.value,
                            price: inputPriceModal.value
                        })
                    })
                        .then(res => {
                            if (res.status == 403) {
                                alert("No access rights");
                            } else {
                                res.json().then(json => alert(json.message));
                            }
                        })
                        // .then(res => res.json())
                        // .then(json => alert(json.message))
                        .then(() => location.reload())
                    orderCompleteModal.classList.remove('modal-show');
                });
            });

        }
    }
}