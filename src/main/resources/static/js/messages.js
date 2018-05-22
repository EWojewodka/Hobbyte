var messages = {
	'internal.error' : 'Internal error. Try again.'
};

function getMessage(code,variables) {
	return messages[code];
}