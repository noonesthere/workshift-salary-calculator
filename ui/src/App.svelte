<script lang="ts">
  import WorkShiftModal from "./workshift/WorkShiftModal.svelte";
  import api from "./api/api";
  import { showToast } from "./stores/toasts";
  import type { WorkShift } from "./api/types";
  import WorkShiftList from "./workshift/WorkShiftList.svelte";
  import PeriodRange from "./workshift/PeriodRange.svelte";

  let modalOpen = $state(false);

  function formatLocal(date: Date): string {
    const y = date.getFullYear();
    const m = String(date.getMonth() + 1).padStart(2, "0");
    const d = String(date.getDate()).padStart(2, "0");
    return `${y}-${m}-${d}`;
  }

  const today = new Date();
  const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);

  let from = $state(formatLocal(firstDay));
  let to = $state(formatLocal(today));

  $inspect(from).with(console.trace);
  $inspect(to).with(console.trace);

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

    <PeriodRange {from} {to}/>

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
    <WorkShiftList {from} {to} />
  </div>
</div>

<WorkShiftModal open={modalOpen} onClose={closeModal} onSave={handleSave} />
