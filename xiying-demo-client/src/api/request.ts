const BASE_URL = "http://localhost:3000";

export function get(url: string, params?: object, config?: object) {
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${BASE_URL}${url}`,
			method: 'GET',
			data: params,
			header: {
				'Content-Type': 'application/json;charset=UTF-8',
			},
			success: (response) => {
				if (response.statusCode === 200) {
					resolve(response.data);
				} else {
					reject(new Error(`Request failed with status code ${response.statusCode}`));
				}
			},
			fail: (error) => {
				reject(error);
			}
		});
	});
}

export function post(url: string, data: object = {}, config?: object) {
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${BASE_URL}${url}`,
			method: 'POST',
			data: data,
			header: {
				'Content-Type': 'application/json;charset=UTF-8',
				
			},
			success: (response) => {
				if (response.statusCode === 200) {
					resolve(response.data);
				} else {
					reject(new Error(`Request failed with status code ${response.statusCode}`));
				}
			},
			fail: (error) => {
				reject(error);
			}
		});
	});
}

export function getBaseUrl() {
	return BASE_URL;
}