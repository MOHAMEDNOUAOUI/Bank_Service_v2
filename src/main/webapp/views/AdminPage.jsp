<%@ page import="java.util.List" %>
<%@ page import="com.wora.bankservice.entity.Demande" %>
<%@ page import="com.wora.bankservice.entity.DemandeStatut" %>
<%@ page import="com.wora.bankservice.entity.Statut" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%List<Demande> DemandeList = (List<Demande>) request.getAttribute("DemandeList");%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/cssfordemand.css">
    <style>

    </style>
    <title>Demande List</title>
</head>
<body>
<div class="container">
    <h1>Demande List</h1>
    <form id="dateform" action="/Admin" method="post" class="container-next">
        <input name="date" pattern="yyyy-mm-dd" type="date" id="date-filter" placeholder="Filter">
        <select name="statut">
            <option selected value="0">ALL</option>
            <option value="1">ACCEPTED</option>
            <option value="2">CANCELED</option>
            <option value="3">REFUSED</option>
            <option value="4">PENDING</option>
        </select>
    </form>
    <%
        if (DemandeList != null && !DemandeList.isEmpty()) {
    %>
    <table id="demandeTable">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Telephone</th>
            <th>Date de naissance</th>
            <th>Date dembauche</th>
            <th>CIN</th>
            <th>Email</th>
            <!-- Add more table headers as needed -->
        </tr>
        </thead>
        <tbody>
        <% for (Demande d : DemandeList) {
            List<DemandeStatut> statusList = new ArrayList<>(d.getDemandeStatuts());
            DemandeStatut lastStatut = statusList.get(statusList.size() - 1);
        %>
        <tr data-statut="<%= lastStatut.getStatut().getId()%>" onclick="callpopup(<%= d.getId() %>)">
            <td><%= d.getNom() %>
            </td>
            <td><%= d.getPrenom() %>
            </td>
            <td><%= d.getTelephone() %>
            </td>
            <td><%= d.getDatedebauche() %>
            </td>
            <td><%= d.getDatedebauche() %>
            </td>
            <td><%= d.getCIN() %>
            </td>
            <td><%= d.getEmail() %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p class="empty-message">No demandes found.</p>
    <% } %>
</div>

<div id="popup" class="popup">
    <div class="minipopup"></div>
    <div class="popup-content">
        <span class="close-btn" onclick="closePopup()">&times;</span>
        <div id="popupContent">

        </div>
    </div>
</div>

<script>
    var popupdata = null;

    const demandeData = {
        <% for (Demande d : DemandeList) { %>
        "<%= d.getId() %>": {
            id: <%= d.getId() %>,
            nom: "<%= d.getNom() %>",
            prenom: "<%= d.getPrenom() %>",
            telephone: "<%= d.getTelephone() %>",
            datedenaissance: "<%= d.getDatenaissance() %>",
            datedebauche: "<%= d.getDatedebauche() %>",
            cin: "<%= d.getCIN() %>",
            email: "<%= d.getEmail() %>",
            montant: "<%= d.getMontant() %>",
            civilite: "<%= d.getCivilite() %>",
            projectType: "<%= d.getMonproject() %>",
            type: "<%= d.getJesuis() %>",
            duree: "<%= d.getDuree() %>",
            mensualite: "<%= d.getMensualite() %>",
            totalRevenue: "<%= d.getTotalrevenue() %>",
            demandeStatutList: [
                <%
                Set<DemandeStatut> demandeStatuts = d.getDemandeStatuts();
                List<DemandeStatut> statusList = new ArrayList<>(demandeStatuts);

                statusList.sort(Comparator.comparing(DemandeStatut::getDateInsert));

                for (int i = 0; i < statusList.size(); i++) {
                    DemandeStatut statut = statusList.get(i);
                %>
                {
                    statut: "<%= statut.getStatut().getStatut() %>",
                    dateInsert: "<%= statut.getDateInsert() %>"
                }<% if (i < statusList.size() - 1) { %>, <% } %>
                <%
                }
                %>
            ]
        }<% if (!d.equals(DemandeList.get(DemandeList.size() - 1))) { %>, <% } %>
        <% } %>
    };

    function getmoredata(id) {
        const demande = demandeData[id];
        if (demande) {
            var statusDetails = "";
            var content =
                "<p id='dateandname'>" + demande.nom + ", " + demande.prenom + " <span>" + formatDate(demande.datedebauche) + "</span></p>" +
                "<h4>" + demande.projectType + "</h4>" +
                "<div>" +
                "<p><span>Description:</span> The ID of the demand is <strong>" + demande.id + "</strong>, " +
                "the type of this demand is <strong>" + demande.type + "</strong>, " +
                "and the duration is <strong>" + demande.duree + "</strong>. " +
                "Here are more details: " +
                "<strong>Téléphone:</strong> " + demande.telephone + ", " +
                "<strong>Date de naissance:</strong> " + formatDate(demande.datedenaissance) + ", " +
                "<strong>CIN:</strong> " + demande.cin + ", " +
                "<strong>Email:</strong> " + demande.email + ", " +
                "<strong>Montant:</strong> " + demande.montant + ", " +
                "<strong>Civilité:</strong> " + demande.civilite + ", " +
                "<strong>Mensualité:</strong> " + demande.mensualite + ", " +
                "<strong>Statuts:</strong> <br>" + statusDetails +
                "<strong>Revenu total:</strong> " + demande.totalRevenue + ".</p>" +
                "</div>" +
                "<button class='gobackbuttons' onclick='findhistory(" + id + ")'>Historique</button>";
            document.getElementById('popupContent').innerHTML = content;
            console.log(demande.nom);
            document.getElementById('popup').style.display = 'flex';
        }
    }


    function callpopup(id) {
        getmoredata(id);
    }

    function closePopup() {
        document.querySelector('#popup').style.display = 'none';
    }


    document.querySelector('#date-filter').addEventListener('input', function () {
        document.querySelector("#dateform").submit();
    })


   /* function CreateDemandInsideHtml(demand, laststatutid) {
        const container = document.querySelector('#demandeTable tbody');
        const row = document.createElement('tr');
        row.setAttribute('onclick', "callpopup(" + demand.id + ")");
        row.setAttribute('data-statut', laststatutid);
        var content = "<td>" + demand.nom + "</td>" +
            "<td>" + demand.prenom + "</td>" +
            "<td>" + demand.telephone + "</td>" +
            "<td>" + demand.datedenaissance + "</td>" +
            "<td>" + demand.datedebauche + "</td>" +
            "<td>" + demand.cin + "</td>" +
            "<td>" + demand.email + "</td>";
        row.innerHTML = content;
        container.appendChild(row);
    }
    */

    function formatDate(dateString) {
        const options = {year: 'numeric', month: 'long', day: 'numeric' , hour: '2-digit' , minute: '2-digit' , hour12:true};
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', options);
    }


    function findhistory(id) {
        const demande = demandeData[id];
        const popupContent = document.getElementById('popupContent');
        popupContent.classList.add('slide-out');
        popupdata = popupContent.innerHTML;

        setTimeout(() => {
            let historyContent = "<ul>";
            demande.demandeStatutList.forEach(demandestatut => {
                historyContent += "<li><strong>Statut:</strong> " + demandestatut.statut +
                    ", <strong>Date:</strong> " + formatDate(demandestatut.dateInsert) + "</li>";
            });
            historyContent += "</ul>";
            historyContent += "<div id='buttonsholder'>";
            historyContent += "<button type='button' class='gobackbuttons' onclick='goback()'>Go Back</button>";
            const statut = demande.demandeStatutList[demande.demandeStatutList.length -1].statut;
            historyContent += "<form id='formstatut' method='POST' action='/Admin'>";
            if(statut === "CANCELED" || statut ==="REFUSED"){
                historyContent += "<button type='button' value='renew' onclick='buttonsAdmin(this , "+id+")' class='renew'></button>";
            }else if(statut === "ACCEPTED"){
                historyContent += "<button type='button'  value='cancel' onclick='buttonsAdmin(this , "+id+")' class='cancel'></button>";
                historyContent += "<button type='button' value='reject' onclick='buttonsAdmin(this , "+id+")' class='reject'></button>";
            }else if(statut === "PENDING"){
                historyContent += "<button type='button' value='accept' onclick='buttonsAdmin(this , "+id+")' class='accept'></button>";
                historyContent += "<button type='button' value='cancel' onclick='buttonsAdmin(this , "+id+")' class='cancel'></button>";
                historyContent += "<button type='button' value='reject' onclick='buttonsAdmin(this , "+id+")'  class='reject'></button>";
            }
            historyContent += "</form>"
            historyContent += "</div>";
            popupContent.innerHTML = historyContent;
            popupContent.classList.remove('slide-out');
        }, 500);
    }

    function goback() {
        const popupContent = document.getElementById('popupContent');
        popupContent.classList.add('slide-out');
        setTimeout(() => {
            popupContent.innerHTML = popupdata;
            popupContent.classList.remove('slide-out');
        }, 500);
    }


    document.querySelector('select').addEventListener('change', function () {
        const selectedStatut = this.value;
        const rows = document.querySelectorAll('tbody tr');
        rows.forEach(row => {
            const statutValue = row.getAttribute('data-statut');
            if (selectedStatut == 0 || statutValue == selectedStatut) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    });


    function buttonsAdmin(e , id){
        console.log(e.value);
        const form = document.getElementById('formstatut');
        let input = document.createElement("input");
        let inputid = document.createElement("input");
        //input statut type
        input.setAttribute("name" , "statut");
        input.setAttribute("id" , "statutinput");
        input.setAttribute("type" , "hidden");
        input.setAttribute("value" , e.value);
        //input demande id
        inputid.setAttribute("name" , "demandeid");
        inputid.setAttribute("id" , "statutid");
        inputid.setAttribute("type" , "hidden");
        inputid.setAttribute("value",id);
        form.appendChild(input);
        form.appendChild(inputid);
        document.querySelector('#formstatut').submit();
    }

</script>
</body>
</html>