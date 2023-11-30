export function isPhoneNumberValid(input) {
  //check if it has 10 characters
  if (!/^\d{10}$/.test(input)) {
    return "Phone number must be ten digits.";
  }

  //check if input isnt null
  if (input === null) {
    return;
  }

  //checking for list validation
  if (input === "1234567890") {
    return "Phone number can't be 1234567890";
  }

  //checking for repeated characters
  if (/^(\d)\1{9}$/.test(input)) {
    return "Phone number can not consist of ten identical numbers.";
  }

  //first or 4th cant be 0 or 1
  if (
    input[0] === "0" ||
    input[0] === "1" ||
    input[3] === "0" ||
    input[3] === "1"
  ) {
    return "First or fourth digit can not be 0 or 1";
  }
  return;
}
