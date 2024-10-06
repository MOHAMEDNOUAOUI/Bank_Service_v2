//Secion1
var section1 = document.querySelector('.Simuler');
var section2 = document.querySelector('.cordonee');
var section3 = document.querySelector('.personnel');
var rightcontainer = document.querySelector('.rightcontainer');
var inputmonths = document.querySelector('.duree').querySelector('input[type="range"]') ,
    output = document.querySelector('.duree').querySelector('input[type="text"]')
;
var pricerange = document.getElementById('montant_range');
var mentualiterange = document.getElementById('mensualite_range');


//Section2
var inputemail = document.getElementById('email').value;
var inputphone = document.getElementById('telephon').value;


// //first page Continue button
function AddSection1Data() {
    var selectprject = document.getElementById('monproject').value;
    var jesuis = document.querySelector('#jesuis_select').value;
    var montant = document.querySelector('#montant_endh_text_aread').value;
    var dure = document.querySelector('#dure').value;
    var mensualite = document.querySelector('#mensualite').value;


    if(selectprject && jesuis && montant && dure && mensualite){

        document.querySelector('#pret').innerText = selectprject;
        var divDetails = document.querySelector('.Détails');
        if (divDetails) {

            divDetails.innerHTML = `
                <h2>Details de mon credit</h2>
                <p>Vous etes: <span>${jesuis}</span></p>
                <p>Montant: <span>${montant} DH</span></p>
                <p>Duree: <span>${dure} mois</span></p>
                <p>Mensualite: <span>${mensualite} DH</span></p>
            `;
        } else {
            divDetails = document.createElement('div');
            divDetails.classList.add('Détails');
            divDetails.innerHTML = `
                <h2>Details de mon credit</h2>
                <p>Vous etes: <span>${jesuis}</span></p>
                <p>Montant: <span>${montant} DH</span></p>
                <p>Duree: <span>${dure} mois</span></p>
                <p>Mensualite: <span>${mensualite} DH</span></p>
            `;
            document.querySelector('.rightcontainer').appendChild(divDetails);
        }



        return true;
    }else{
        console.log('Please fill all the fields');
        return false;
    }

}

function AddSection2Data() {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const phoneRegex = /^\+?[1-9]\d{1,14}(\s|-)?(\(?\d{1,4}\)?|\d+)(\s|-)?(\d+(\s|-)?)*$/;

    if(!emailRegex.test(inputemail.value)){
        alert('Wrong email');
        return false;
    }

    if(!phoneRegex.test(inputphone.value)){
        alert('wrong phone');
        return false;
    }

    var UserDetails = document.querySelector('.UserDetails');

    if(UserDetails){
        UserDetails.innerHTML = `
            <h2>Coordonnees et infos personnelles</h2>
                <p>Email: <span>${inputemail.value}</span></p>
                <p>Telephone: <span>${inputphone.value} DH</span></p>
            `
    }else{
        UserDetails = document.createElement('div');
        UserDetails.classList.add('UserDetails');
        UserDetails.innerHTML = `
            <h2>Coordonnees et infos personnelles</h2>
                <p>Email: <span>${inputemail.value}</span></p>
                <p>Telephone: <span>${inputphone.value} DH</span></p>
            `
    }


    document.querySelector('.pretpersonel').insertAdjacentElement('afterend' , UserDetails);


    return true;
}


///////////

let currentSection = 1;
let maxSectionReached = 1;

function nextSection(sectionNumber) {

    if(sectionNumber == 2){
        if(AddSection1Data()){
            showSection(sectionNumber);
            maxSectionReached = 2;
        }
    }

    if(sectionNumber==3){
        if(AddSection2Data()){
            showSection(sectionNumber);
            maxSectionReached = 3;
        }
    }

    console.log(maxSectionReached);


    changingColors(maxSectionReached);


}


function showSection(sectionNumber) {
    section1.style.display = 'none';
    section2.style.display = 'none';
    section3.style.display = 'none';

    document.querySelectorAll('.NumbersPickers').forEach((e) => {
        e.classList.remove('active');
    })

    changingColors(maxSectionReached);
    console.log("sectionnumber" + sectionNumber);


    if(sectionNumber == 1){

        if(!document.querySelector('.first').classList.add('active')){
            document.querySelector('.first').classList.add('active');
        }
        section1.style.display = "flex";
    }else if(sectionNumber == 2) {
        if(!document.querySelector('.second').classList.add('active')){
            document.querySelector('.second').classList.add('active');
        }
        section2.style.display = "flex";
    }
    else{
        document.querySelector('.last').classList.add('active');
        section3.style.display = "flex";
    }



    currentSection = sectionNumber;
}


function goToSection(e,sectionNumber) {
    if (sectionNumber <= maxSectionReached) {
        showSection(sectionNumber);
    }
}


showSection(currentSection);




function changingColors(maxSectionReached) {
    if(maxSectionReached == 3){
        document.querySelector('.first').style.backgroundColor ='rgb(2,140,150)';
        document.querySelector('.second').style.backgroundColor = 'rgb(2,140,150)';
        document.querySelector('.last').style.backgroundColor = 'rgb(2,140,150)';
    }
    else if(maxSectionReached ==2){
        document.querySelector('.second').style.backgroundColor = 'rgb(2,140,150)';
        document.querySelector('.first').style.backgroundColor ='rgb(2,140,150)';
    }
}