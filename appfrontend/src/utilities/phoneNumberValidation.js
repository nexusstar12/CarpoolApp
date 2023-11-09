export function isPhoneNumberValid(input) {
  //check if it has 10 characters
  if (!/^\d{10}$/.test(input)) {
    return false;
  }

  if (/^(.)\1*$/.test(input)) {
    return false;
  }
  if (input === "123456789") {
    return false;
  }

  if (
    input[0] === "0" ||
    input[0] === "1" ||
    input[3] === "0" ||
    input[3] === "1"
  ) {
    return false;
  }
  return true;
}
