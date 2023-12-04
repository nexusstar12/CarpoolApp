export function convertTimeZoneToUTC(datetime, timezoneOffset) {
  const offsetHours = parseInt(timezoneOffset);

  const date = new Date(datetime);

  date.setMinutes(
    date.getMinutes() + offsetHours * 60 + date.getTimezoneOffset()
  );

  return date.toISOString().slice(0, 19);
}
