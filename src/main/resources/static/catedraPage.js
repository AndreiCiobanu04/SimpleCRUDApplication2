function specificCatedra(id){
    let response = fetch('http://localhost:8080/profesoriForSpecificCatedra/' + id);
    let data = response.then(response=>{
        console.log("Catedre");
        console.log(response.json().then(res=> {
            console.log(res);
            let result = "";
            for (let i = 0; i < res.length; i++) {
                result += "<tr class='trProfesor'>";
                result += "<td>" + res[i].id + "</td>";
                result += "<td>" + res[i].nume + "</td>";
                result += "<td>" + res[i].prenume + "</td>";
                result += "<td>" + res[i].varsta + "</td>";
                result += "<td>" + res[i].specializare + "</td>";
                result += "<td><button onclick=deleteSpecificProfesor("+res[i].id+")>Delete</button></td>";
            }
            result += "</tr>";
            result += "</table>";
            document.getElementById('profesoriTableForSpecificCatedra').innerHTML = result;
            return result;
        }))});
}

function  availableSpecificCatedra(id){
    let response = fetch('http://localhost:8080/profesoriAvailableForSpecificCatedra/' + id);
    let data = response.then(response=>{
        console.log(response.json().then(res=> {
            console.log(res);
            let result = "<option selected>Choose...</option>";
            for (let i = 0; i < res.length; i++) {
                result += "<option value=" + res[i].id +">"+ res[i].nume+ "  " + res[i].prenume + "</option>";
            }
            document.getElementById('custom-select-catedra').innerHTML = result;
            return result;
        }))});
}

