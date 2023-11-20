export function isZipCodeValid(input) {
    if (input.length !== 5) {
        return false;
    }
    if (!/^\d+$/.test(input)) {
        return false;
      }
    return true;
}