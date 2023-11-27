export function isCityNameValid(input) {
  var regex = /^[A-Za-z ]+$/;

  if (input.length > 85) {
    return false;
  }

  return regex.test(input);
}
