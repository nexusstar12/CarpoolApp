export function getBrowserTimezone() {
  const offset = new Date().getTimezoneOffset();
  const hours = Math.abs(Math.floor(offset / 60));
  return (offset <= 0 ? "+" : "-") + String(hours).padStart(2, "0");
}
