const ctxMain = document.getElementById('mainChart').getContext('2d');


const mainChart = new Chart(ctxMain, {
    type: 'bar',
    data: {
        labels: labels != null ? labels : null,
        datasets: [{
            label: '수익',
            data: dataValues != null ? dataValues : null,
            backgroundColor: '#03DAC650',
            borderColor: '#BB86FC',
            borderWidth: 1
        }]
    },
    options: {
		responsive: true,
		maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

const ctxSub = document.getElementById('subChart').getContext('2d');


const subChart = new Chart(ctxSub, {
  type: 'doughnut',
  data: {
          labels: labels != null ? labels : null,
          datasets: [{
              label: '수익',
              data: dataValues != null ? dataValues : null,
              backgroundColor: '#03DAC650',
              borderColor: '#BB86FC',
          }]
      },
  options: {
    responsive: true,
    plugins: {
		legend: {
	        display: false
	    }
    }
  },
});

const selectBtns = document.querySelectorAll(".selectBtn")
selectBtns.forEach(btn => {
	if (btn.id == state) {
		btn.style.border = "#BB86FC 3px solid"
	}
	
	else {
		btn.style.border = "none"
	}
})
