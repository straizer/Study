/* jshint esversion: 6 */
document.addEventListener('DOMContentLoaded', function () {
	"use strict";
	const healthCards = document.querySelectorAll('.health-card');
	const connectCards = document.querySelectorAll('.connect-card');
	const logo = document.querySelector('.logo');

	function handleMouseEnter(event) {
		const icon = event.currentTarget.querySelector('.fit-icon');
		if (icon) {
			icon.style.transition = 'transform 0.3s ease';
			icon.style.transform = 'translateY(-50%) scale(1.5)';
		}
	}

	function handleMouseLeave(event) {
		const icon = event.currentTarget.querySelector('.fit-icon');
		if (icon) {
			icon.style.transition = 'transform 0.3s ease';
			icon.style.transform = 'translateY(-50%) scale(1)';
		}
	}

	function rotateLogo() {
		logo.style.transition = 'transform 0.3s ease';

		setTimeout(() => { logo.style.transform = 'rotate(90deg)'; }, 0);
		setTimeout(() => { logo.style.transform = 'rotate(180deg)'; }, 300);
		setTimeout(() => { logo.style.transform = 'rotate(270deg)'; }, 600);
		setTimeout(() => { logo.style.transform = 'rotate(360deg)'; }, 900);

		setTimeout(() => {
			logo.style.transition = '';
			logo.style.transform = '';
		}, 1200);
	}

	healthCards.forEach(card => {
		card.addEventListener('mouseenter', handleMouseEnter);
		card.addEventListener('mouseleave', handleMouseLeave);
	});

	connectCards.forEach(card => {
		card.addEventListener('mouseenter', handleMouseEnter);
		card.addEventListener('mouseleave', handleMouseLeave);
	});

	if (logo) {
		logo.addEventListener('mouseenter', rotateLogo);
	}
});
