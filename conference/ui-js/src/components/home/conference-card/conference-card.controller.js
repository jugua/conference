export default class {
  constructor() {
    const tbdString = 'TBD';

    // Format date output from YYYY-MM-DD ISO 8601 format we receive from server to human readable MM/DD/YYYY
    function format(isoDate) {
      return `${isoDate.substr(8, 2)}/${isoDate.substr(5, 2)}/${isoDate.substr(0, 4)}`;
    }

    this.startDate = this.data.start_date ? format(this.data.start_date) : tbdString;
    this.endDate = this.data.end_date ? format(this.data.end_date) : tbdString;
    this.cfpStartDate = this.data.call_for_paper_start_date ? format(this.data.call_for_paper_start_date) : tbdString;
    this.cfpEndDate = this.data.call_for_paper_end_date ? format(this.data.call_for_paper_end_date) : tbdString;
  }
}