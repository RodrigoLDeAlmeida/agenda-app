/**
 * Confirmação de exclusão de um contato
 * @author Rodrigo
 */

function confirmar(idcon){
	let resposta = confirm("Confirmar a exclusão deste contato?");
	if(resposta === true){
		//alert(idcon);
		window.location.href = "delete?idcon=" + idcon;
	}
}