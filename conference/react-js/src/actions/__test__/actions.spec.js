import applyFilters from '../filter';

describe('Actions', () => {
  it('Apply Filters', () => {
    const filter = {
      filter: {
        filter: {
          topic: '',
          status: '',
        },
        data: [
          {},
        ],
      },
    };

    const apply = applyFilters(filter);
    expect(apply).toEqual({ type: 'APPLY_FILTERS', filter });
  });
});
