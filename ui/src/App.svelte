<script lang="ts">
  import api from "./api/api";
  import Toasts from "./components/Toasts.svelte";
  import { showToast } from "./stores/toasts";

  let startDate: string = $state("");
  let beginAt: string = $state("");
  let finishedAt: string = $state("");
  let bid: number = $state(0);
  let workedHours: number = $derived(calculateHours());
  let workShiftSalary: number = $derived(bid * workedHours);

  function calculateHours(): number {
    if (beginAt && finishedAt) {
      const start = parseTimeToMinutes(beginAt);
      const end = parseTimeToMinutes(finishedAt);

      let diff = end - start;

      // If negative → shift passes midnight
      if (diff < 0) {
        diff += 24 * 60;
      }

      return diff / 60;
    } else {
      return 0;
    }
  }

  function onSaved(): void {}

  function parseTimeToMinutes(time: string): number {
    const [h, m] = time.split(":").map(Number);
    return h * 60 + m;
  }

  async function onSubmit(e: Event) {
    e.preventDefault();

    const payload = {
      startDate,
      beginAt,
      finishedAt,
      bid,
      workedHours,
      workShiftSalary,
    };

     let result = await api.post("workshifts", payload);
    showToast("Saved Work {result}" );
    // onsaved(user);
  }
</script>

<main class="min-h-screen bg-gray-100 flex items-center justify-center p-4">
  <div class="w-full max-w-xl bg-white rounded-2xl shadow-xl p-8 space-y-8">
    <!-- Header -->
    <div class="space-y-1">
      <h1 class="text-2xl font-bold text-gray-900">Облік робочої зміни</h1>
      <p class="text-sm text-gray-500">
        Введіть дані зміни та отримайте автоматичний розрахунок
      </p>
    </div>

    <!-- Form -->
    <form onsubmit={onSubmit} class="space-y-6">
      <!-- Date -->
      <div class="space-y-2">
        <label for="startDate" class="text-sm font-medium text-gray-700">
          Дата
        </label>
        <input
          id="startDate"
          type="date"
          bind:value={startDate}
          class="w-full rounded-xl border-gray-300 focus:border-primary-500 focus:ring-primary-500"
        />
      </div>

      <!-- Time Grid -->
      <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
        <div class="space-y-2">
          <label for="beginAt" class="text-sm font-medium text-gray-700">
            Початок
          </label>
          <input
            id="beginAt"
            type="time"
            bind:value={beginAt}
            class="w-full rounded-xl border-gray-300 focus:border-primary-500 focus:ring-primary-500"
          />
        </div>

        <div class="space-y-2">
          <label for="finishedAt" class="text-sm font-medium text-gray-700">
            Кінець
          </label>
          <input
            id="finishedAt"
            type="time"
            bind:value={finishedAt}
            class="w-full rounded-xl border-gray-300 focus:border-primary-500 focus:ring-primary-500"
          />
        </div>
      </div>

      <!-- Hourly Rate -->
      <div class="space-y-2">
        <label for="bid" class="text-sm font-medium text-gray-700">
          Ставка (грн/год)
        </label>
        <input
          id="bid"
          type="number"
          bind:value={bid}
          placeholder="0"
          class="w-full rounded-xl border-gray-300 focus:border-primary-500 focus:ring-primary-500"
        />
      </div>

      <!-- Summary Card -->
      <div class="bg-gray-50 rounded-xl p-5 border border-gray-200 space-y-3">
        <div class="flex justify-between text-sm text-gray-600">
          <span>Відпрацьовано</span>
          <span class="font-medium text-gray-900">
            {workedHours.toFixed(2)} год
          </span>
        </div>

        <div class="flex justify-between text-sm text-gray-600">
          <span>Ставка</span>
          <span class="font-medium text-gray-900">
            {bid} грн/год
          </span>
        </div>

        <div class="border-t pt-3 flex justify-between items-center">
          <span class="text-base font-semibold text-gray-800">
            До виплати
          </span>
          <span class="text-2xl font-bold text-primary-600">
            {workShiftSalary.toFixed(2)} грн
          </span>
        </div>
      </div>

      <!-- Submit Button -->
      <button
        type="submit"
        class="w-full bg-primary-500 hover:bg-primary-600 text-white font-semibold py-3 rounded-xl transition-all duration-200 active:scale-[0.98] cursor-pointer"
      >
        Зберегти зміну
      </button>
    </form>
  </div>
</main>

<Toasts />

<style>
</style>
