const urlUsers = 'http://localhost:8081/users'
const urlLogin = 'http://localhost:8081/login'
const urlLogout = 'http://localhost:8081/logout2'
const urlCars = 'http://localhost:8081/cars'
const urlOrders = 'http://localhost:8081/orders'
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
            'Content-Type': 'application/json'
        },
        body: data
    })
        .then(res => res.json())
        .then(json => alert(json.message))
    // .then(() => location.reload())
}

const get = function (url) {
    fetch(url, {
        method: 'GET'
    })
        .then(res => res.json())
        .then(json => alert(json.message))
        .then(() => location.reload())
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
        post(urlUsers,
            JSON.stringify({
                login: registerModalForm.login.value,
                password: registerModalForm.password.value,
                email: registerModalForm.email.value
            }))
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
});


//tab profile
const tpProfile = document.querySelector('.tp-profile');
if (tpProfile) {
    let id;
    const tpProfileForm = tpProfile.querySelector('.form');
    const inputProfile = document.querySelector('.input-profile');
    const btnFormEdit = tpProfile.querySelector('.btn-edit');
    const btnFormSaveChange = tpProfile.querySelector('.btn-save-change');
    inputProfile.addEventListener('click', () => {
        fetch(`${urlUsers}/bylogin/${authName.innerHTML}`, {
            method: 'GET'
        })
            .then(res => res.json())
            .then(json => {
                console.log(json);
                id = json.id;
                tpProfileForm.login.value = json.login;
                tpProfileForm.email.value = json.email;
                tpProfileForm.role.value = json.role.name;
            })
    });

    btnFormEdit.addEventListener('click', () => {
        tpProfileForm.login.removeAttribute("disabled");
        tpProfileForm.email.removeAttribute("disabled");
        btnFormSaveChange.removeAttribute("hidden");

        btnFormSaveChange.addEventListener("click", () => {
            fetch(urlUsers, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: id,
                    login: tpProfileForm.login.value,
                    email: tpProfileForm.email.value,
                })
            })
                .then(res => res.json())
                .then(json => {
                    alert(json.message);
                    tpProfileForm.login.setAttribute("disabled", "disabled");
                    tpProfileForm.email.setAttribute("disabled", "disabled");
                    btnFormSaveChange.setAttribute("hidden", "hidden");
                })
                .catch(error => console.log('There was an error:', error))
        })
    });
}

//tab orders
const tpCars = document.querySelector('.tp-cars');
const editCarModal = document.querySelector('.edit-car-modal');
const editCarModalForm = editCarModal.querySelector('.form');

if (tpCars) {
    const tpCarsTable = tpCars.querySelector('.table-cars');
    const btnGetAvailableCars = tpCars.querySelector('.btn-get-available');
    const btnGetAllCars = tpCars.querySelector('.btn-get-all');

    function renderCars(cars) {
        let id;
        for (let i in cars) {
            const tr = `
    <tr data-id='${cars[i].id}'>
      <td>${cars[i].id}</td>
      <td>${cars[i].carModel.carMake.name}</td>
      <td>${cars[i].carModel.name}</td>
      <td>${cars[i].carStatus.status}</td>
      <td>
      <input type="radio">
<!--        <button class="btn btn-edit">Edit</button>-->
<!--        <button class="btn btn-delete">Delete</button>-->
      </td>
    </tr>
  `;
            tpCarsTable.insertAdjacentHTML('beforeend', tr)
        }
    }

    btnGetAvailableCars.addEventListener('click', () => {
        fetch(urlCars + "/status-available", {
            method: 'GET'
        })
            .then(res => res.json())
            .then(json => {
                renderCars(json);
            })
    })
    btnGetAllCars.addEventListener('click', () => {
        fetch(urlCars, {
            method: 'GET'
        })
            .then(res => res.json())
            .then(json => {
                renderCars(json);
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
            .then(res => res.json())
            .then(json => {
                for (let i in json) {
                    selectCars.options[i] = new Option(json[i].carModel.carMake.name + " " + json[i].carModel.name, json[i].id);
                }
            }).then(selectedIdCar = selectCars.value)
    });

    selectCars.addEventListener("change", () => {
        selectedIdCar = selectCars.value;
        var stDate = inStDate.value.split(".").reverse();
        var endDate = inEndDate.value.split(".").reverse();
        post(urlOrders, JSON.stringify(
            {
                car: {id: selectedIdCar},
                customer: {id: 1},
                // startTime: stDate,
                // endTime: endDate
            }
        ));
    });


    btnOrderSubmit.addEventListener("click", () => {
        fetch(`${urlUsers}/bylogin/${authName.innerHTML}`, {
            method: 'GET'
        })
            .then(res => res.json())
            .then(() => {
            })
    });
}