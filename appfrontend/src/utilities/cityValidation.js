export function isCityNameValid(input) {
    if (input.length > 85) {
      return false;
    }
  
    if (!/^[a-zA-Z]+$/.test(input)) {
      return false;
    }
  
    return true;
  }
  