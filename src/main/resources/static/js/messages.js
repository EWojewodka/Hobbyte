var messages = {
	'internal.error' : 'Internal error. Try again.',
	'enter.content' : 'Write a comment...'
};

function getMessage(code,variables) {
	return messages[code];
}