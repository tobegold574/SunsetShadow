import { defineStore } from 'pinia';

export const useStatus = defineStore('status', {
  state: () => ({
    id: -1,
    message: '',
	  username: '',
    shareLocation:false
  }),
  getters: {},
  actions: {
    setId(ID: number) {
      this.id = ID;
    },
    setMessage(message: string) {
      this.message = message;
    },
	setUsername(username: string) {
		this.username = username;
	},
	setShareLocation(share:boolean){
		this.shareLocation=share
	}
  },
});