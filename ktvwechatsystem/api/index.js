// 接口注册

import custom from './custom.js'
import base from './base.js'

const api = {}

const modules = {
	custom,
	base
}


for (let key in modules) {
	api[key] = (time = 100) => {
		return new Promise(reslove => {
			setTimeout(() => {
				reslove(modules[key])
			}, time)
		})
	}
}

export default api
