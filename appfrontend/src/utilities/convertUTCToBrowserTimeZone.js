export function convertFromUTC(utcDateTime, timezoneOffset) {
  const date = new Date(utcDateTime + "Z");
  const offsetHours = parseInt(timezoneOffset);
  const totalOffset = offsetHours * 60;

  date.setMinutes(date.getMinutes() + totalOffset);

  return date.toISOString().slice(0, 19);
}
