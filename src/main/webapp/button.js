document.querySelectorAll(".memo_item").forEach((item) => {
  const deleteForm = item.querySelector(".delete_form");
  let hideTimer;

  item.addEventListener("mouseenter", () => {
    clearTimeout(hideTimer);
    deleteForm.style.display = "block";
  });

  item.addEventListener("mouseleave", () => {
    hideTimer = setTimeout(() => {
      deleteForm.style.display = "none";
    }, 1000); // 2秒後に消す
  });

  // 削除ボタンにマウスを乗せたときに消えないようにする（オプション）
  deleteForm.addEventListener("mouseenter", () => {
    clearTimeout(hideTimer);
  });

  deleteForm.addEventListener("mouseleave", () => {
    hideTimer = setTimeout(() => {
      deleteForm.style.display = "none";
    }, 1000);
  });
});