let openedModal = ""

const openModal = (e) => {
	e.stopPropagation()
	
	const targetId = "modal" + e.target.id
	let modal = document.querySelector("#" + targetId)
	
	console.log(targetId)
	
	modalBtns.forEach(btn => {
		console.log(e.target.id)
		console.log(btn.id)
		if (btn.id == e.target.id) {
			modal.style.display = "block"		
			openedModal = "#" + targetId
		}
		
		else {
			document.querySelector("#modal" + btn.id).style.display = "none"
		}
	})
	
	document.querySelector("#modalUpdate").style.display = "none"
}

const closeModal = () => {
	if (openedModal) {
		document.querySelector(openedModal).style.display = "none"	
	}

	openedModal = ""
}


let modalBtns = document.querySelectorAll(".modalBtn")
modalBtns.forEach((btn) => {
	btn.addEventListener("click", openModal)
})

let modals = document.querySelectorAll(".modal")
modals.forEach((m) => {
	m.addEventListener("click", e => e.stopPropagation())
})

let updateModalBtn = document.querySelector("#updateBtn")
updateModalBtn.addEventListener("click", () => {
	console.log("?");
	document.querySelector("#modalupdate").style.display = "block"
	openedModal = "#modalupdate"
	
	modalBtns.forEach(btn => {
		document.querySelector("#modal" + btn.id).style.display = "none"
	})
})

document.querySelector("html").addEventListener("click", closeModal)
document.querySelector("#insert").addEventListener("click", openModal)