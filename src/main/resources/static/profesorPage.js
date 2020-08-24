function specificProfesor(id){
    let response = fetch('http://localhost:8080/catedreForSpecificProfesor/' + id);
    let data = response.then(response=>{
        console.log("Catedre");
        console.log(response.json().then(res=> {
        console.log("aaa");
            console.log(res);
            let result = "";
            for (let i = 0; i < res.length; i++) {
                result += "<tr class='trCatedra'>";
                result += "<td>" + res[i].id + "</td>";
                result += "<td>" + res[i].nume + "</td>";
                result += "<td>" + res[i].corp + "</td>";
                result += "<td>" + res[i].etaj + "</td>";
                result += "<td>" + res[i].titular + "</td>";
                result += "<td>" + res[i].membri + "</td>";
                result += "<td><button onclick=deleteSpecificCatedra(" + res[i].id + ")>Delete</button></td>";

            }
            result += "</tr>";
            result += "</table>";
            document.getElementById('catedreTableSpecificProfesor').innerHTML = result;
            return result;
        }))});
}

function  availableSpecificProfesor(id){
    let response = fetch('http://localhost:8080/catedreAvailableForSpecificProfesor/' + id);
    let data = response.then(response=>{
        console.log(response.json().then(res=> {
            console.log(res);
            let result = "<option selected>Choose...</option>";
            for (let i = 0; i < res.length; i++) {
                result += "<option value=" + res[i].id +">"+ res[i].nume + "</option>";
            }
            document.getElementById('custom-select-profesor').innerHTML = result;
            return result;
        }))});
}

