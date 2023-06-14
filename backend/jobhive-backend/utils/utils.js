const buildResp = (message, data = null, override = true) => {
  // return {
  //   success:
  //     data == [] || override === false || data === null || data === undefined
  //       ? false
  //       : true,
  //   message: message || "",
  //   data: data === null || data === undefined ? null : data,
  // };
  return data;
};

const cleanStr = (str) => {
  return str.replace(/'/g, "''").trim();
};

module.exports = { buildResp, cleanStr };
