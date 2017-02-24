export default class {
  constructor() {
    const tbdString = 'TBD';
    this.startDate = this.data.start_date ? this.data.start_date : tbdString;
    this.endDate = this.data.end_date ? this.data.end_date : tbdString;
    this.cfpStartDate = this.data.call_for_paper_start_date ? this.data.call_for_paper_start_date : tbdString;
    this.cfpEndDate = this.data.call_for_paper_end_date ? this.data.call_for_paper_end_date : tbdString;
  }
}