// debounce.js
export function debounce (func, delay = 500) {
  let timer

  return function (...args) {
    clearTimeout(timer)
    setTimeout(() => {
      func.call(this, ...args)
    }, delay)
  }
}
