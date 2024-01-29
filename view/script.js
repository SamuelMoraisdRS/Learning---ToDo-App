// o arquivo JavaScript será responsavel pela interatividade da pagina web
const url = "http://localhost:8080/task/user/2";

// Document é a forma de acessar o DOM da página web
// O DOM (Document Object Model) é uma representação da página HTML na memória. O documento é estruturado
// na forma de árvore, criando relações entre os diversos elementos da página HTMl. O próprosito
// do DOM é permitir que o código JavaScript acesse partes distintas do código HTML, realizando alteraçoes no
// estilo, formatacao e comportamento da página de forma direta.

function hideLoader() {
  document.getElementById("loading").style.display = "none";
}

function show(task) {
  let tab = `<thead>
                <th scope="col">#</th>
                <th scope="col">Description</th>
                <th scope="col">Username</th>
                <th scope="col">User Id</th>
            </thead>`;
  for (let t of task) {
    tab += `
            <tr>
                <td scope="row">${t.id}</td>
                <td scope="row">${t.description}</td>
                <td scope="row">${t.user.username}</td>
                <td scope="row">${t.user.id}</td>
            </tr>
    `;
  }

  // Estamos acessando o arquivo DOM na posicao da id 'task' e subtituindo pelo objeto tab, obtido a partir deste
  // script
  document.getElementById("tasks").innerHTML = tab;
}

async function getAPI(url) {
    const response = await fetch(url, {method: "GET"});
    let data = await response.json();
    // console.log("asdsad");
    if (response) {
      hideLoader();
    }
    show(data);
}

getAPI(url);