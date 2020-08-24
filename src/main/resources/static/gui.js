function fetchProfesori (url) {
    let response =  fetch(url);
    let data =  response.then(response => {
        console.log("ok");
    console.log(response.json().then(res=> {
        console.log(res);
    let result = "";
    for (let i = 0; i < res.length; i++) {
        result += "<tr class='trProfesor' onclick=\"window.location.href='http://localhost:8080/profesorPage/" + res[i].id +"'\">";
        result += "<td>" + res[i].id + "</td>";
        result += "<td>" + res[i].nume + "</td>";
        result += "<td>" + res[i].prenume + "</td>";
        result += "<td>" + res[i].varsta + "</td>";
        result += "<td>" + res[i].specializare + "</td>";
    }
    result += "</tr>";
    result += "</table>";
    document.getElementById('tableProfesori').innerHTML = result;
    return result;
}
))});
}



function fetchCatedre(url){
    let response =  fetch(url);
    let data =  response.then(response => {
        console.log(response.json().then(res=> {
            console.log(res);
    let result = "";
    for (let i = 0; i < res.length; i++) {
        result += "<tr class='trCatedra'  onclick=\"window.location.href='http://localhost:8080/catedraPage/" + res[i].id +"'\">";
        result += "<td>" + res[i].id + "</td>";
        result += "<td>" + res[i].nume + "</td>";
        result += "<td>" + res[i].corp + "</td>";
        result += "<td>" + res[i].etaj + "</td>";
        result += "<td>" + res[i].titular + "</td>";
        result += "<td>" + res[i].membri + "</td>";
    }


    result += "</tr>";
    result += "</table>";
    document.getElementById('tableCatedre').innerHTML = result;
    return result;
}
))});
}



function specificProfesor(id){
    let response = fetch('http://localhost:8080/catedreForSpecificProfesor/' + id);
    let data = response.then(response=>{
        console.log("Catedre");
    console.log(response.json().then(t => console.log(t)));
})}

function specificCatedra(id){
    let response = fetch('http://localhost:8080/profesoriForSpecificCatedra/' + id);
    let data = response.then(response=>{
        console.log("Profesori");
    console.log(response.json().then(t => console.log(t)));
})
}
fetchProfesori('http://localhost:8080/allProfesori');
fetchCatedre('http://localhost:8080/allCatedre');


function addNewCatedra(){
    const $form = $("#catedraForm");
    const data = getFormData($form);
    console.log(data);
    fetch('http://localhost:8080/addCatedra',{
        method:'post',
        headers: {
            'Content-Type': 'application/json',
        },
        body:JSON.stringify(data)}).then(t => fetchCatedre('http://localhost:8080/allCatedre'));
}

function addNewProfesor(){
    const $form = $("#profesorForm");
    const data = getFormData($form);
    console.log(data);
    fetch('http://localhost:8080/addProfesor',{
        method:'post',
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        body:JSON.stringify(data)}).then(t=> fetchProfesori('http://localhost:8080/allProfesori'));
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });
    return indexed_array;
}

function addProfesorButton() {
    if(document.getElementById('profesorForm').style.display === 'none')
        document.getElementById('profesorForm').style.display = 'block';
    else
        document.getElementById('profesorForm').style.display = 'none';
}

function addCatedraButton(){
    if(document.getElementById('catedraForm').style.display === 'none')
        document.getElementById('catedraForm').style.display = 'block';
    else
        document.getElementById('catedraForm').style.display = 'none';

}