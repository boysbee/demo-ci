import { test, expect } from '@playwright/test'

test.describe.parallel('API Wallet History', () => {
  
  test('get all history', async ({ request }) => {
    const response = await request.get('http://localhost:8081/wallet-history/api/wallet?startDatetime=2022-03-01T00:00:00%2B00:00&endDatetime=2022-03-03T00:00:00%2B00:00')
    expect(response.status()).toBe(200)
    expect(response.headers()['content-type']).toBe('application/json')
  })
})
