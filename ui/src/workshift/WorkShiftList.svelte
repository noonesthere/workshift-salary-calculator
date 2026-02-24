<script lang="ts">
  import { onMount } from "svelte";
  import api from "../api/api";
  import type { WorkShift } from "../api/types";

  let workShifts = $state<WorkShift[]>([]);
  let loading = $state(true);
  let error = $state<string | null>(null);

  async function load() {
    loading = true;
    error = null;

    try {
      workShifts = await api.get<WorkShift[]>("workshifts");
    } catch (e) {
      console.error(e);
      error = "Не вдалося завантажити зміни";
    } finally {
      loading = false;
    }
  }

  onMount(load);

  function formatDate(date: string) {
    return new Date(date).toLocaleDateString("uk-UA");
  }
</script>

<div class="max-w-4xl mx-auto p-6 space-y-6">
  <!-- Header -->
  <div class="flex items-center justify-between">
    <h2 class="text-2xl font-bold text-gray-900">Робочі зміни</h2>
  </div>

  {#if loading}
    <div class="text-center py-10 text-gray-500 animate-pulse">
      Завантаження...
    </div>
  {:else if error}
    <div class="bg-red-50 text-red-600 p-4 rounded-xl">
      {error}
    </div>
  {:else if workShifts.length === 0}
    <div class="bg-gray-50 text-gray-500 p-6 rounded-2xl text-center">
      Немає збережених змін
    </div>
  {:else}
    <div class="grid gap-4">
      {#each workShifts as shift (shift.id)}
        <div
          class="bg-white rounded-xl border border-gray-200
                 px-4 py-3
                 hover:shadow-sm
                 transition-all duration-150 cursor-pointer"
        >
          <div class="flex justify-between items-center">
            <!-- Left -->
            <div class="space-y-0.5">
              <div class="text-sm font-semibold text-gray-800">
                {formatDate(shift.startDate)}
              </div>

              <div class="text-xs text-gray-500">
                {shift.beginAt} — {shift.finishedAt}
              </div>
            </div>

            <!-- Middle -->
            <div class="text-xs text-gray-600 text-center">
              {shift.workedHours.toFixed(2)} год × {shift.bid}
            </div>

            <!-- Right -->
            <div class="text-sm font-semibold text-primary-600">
              {shift.workShiftSalary.toFixed(2)} грн
            </div>
          </div>
        </div>
      {/each}
    </div>
  {/if}
</div>
