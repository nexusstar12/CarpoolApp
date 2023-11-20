export function isZipCodeValid(input) {
  const regex =
    /^(?!0{5}|1{5}|2{5}|3{5}|4{5}|5{5}|6{5}|7{5}|8{5}|9{5})\d{5}(-\d{4})?$/;

  if (input.length !== 5) {
    return false;
  }

  return regex.test(input);
}
