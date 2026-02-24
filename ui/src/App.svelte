<script lang="ts">
  import WorkShiftModal from "./workshift/WorkShiftModal.svelte";
  import api from "./api/api";
  import { showToast } from "./stores/toasts";
  import type { WorkShift } from "./api/types";
  import WorkShiftList from "./workshift/WorkShiftList.svelte";

  let modalOpen = $state(false);

  function openModal() {
    modalOpen = true;
  }

  function closeModal() {
    modalOpen = false;
  }

  async function handleSave(payload: WorkShift): Promise<void> {
    await api.post("workshifts", payload);
    showToast("Зміну збережено");
    modalOpen = false;
  }
</script>

<div class="h-screen flex flex-col bg-gray-100">
  <!-- Header -->
  <div
    class="flex items-center justify-between px-6 py-4 bg-white shadow-sm border-b"
  >
    <h1 class="text-xl font-semibold text-gray-800">Облік робочих змін</h1>

    <button
      onclick={openModal}
      class="inline-flex items-center gap-2 px-4 py-2
             bg-primary-500 hover:bg-primary-600
             text-white text-sm font-medium
             rounded-xl shadow-sm
             transition-all duration-200
             active:scale-95 cursor-pointer"
    >
      <span class="text-lg leading-none">+</span>
      Додати зміну
    </button>
  </div>

  <!-- Scrollable List Area -->
  <div class="flex-1 overflow-y-auto p-6">
    <WorkShiftList />
  </div>
</div>

<WorkShiftModal open={modalOpen} onClose={closeModal} onSave={handleSave} />
