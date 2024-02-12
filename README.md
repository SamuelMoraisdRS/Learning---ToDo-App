<h1> Usaremos a arquitetura MVC

*Model* -> O modelo dos dados: a camada do codigo que contera as estruturas de dados da aplicacao. Recebe, processa e organiza os dados
*View* -> Interface apresentada ao usuario. Contem os elementos de interacao com usuario. Mostra os dados armazenados no model
*Controller* -> Faz a interacao entre o view e o model. Pega as interacoes captadas no view e manda que o model realize certas operacoes. tambem faz o contrario, comunicando ao view as alteracoes e atualizacoes no model. Trata as requisicoes http


Repository -> arquivos que farão a comunicacao entre a aplicacao e o banco de dados. Ou seja, fará o gerenciamento dos querys (pedidos/consultas) enviadas ao SQL
O spring tem métodos para fazer as querys automaticamente